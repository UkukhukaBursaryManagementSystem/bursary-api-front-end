
package com.ukukhula.bursaryapi.repositories;

import com.ukukhula.bursaryapi.entities.UniversityAllocation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Year;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UniversityAllocationRepository {
    final String SQL = "SELECT * FROM [dbo].[UniversityAllocationsView] WHERE UniversityName = ?";
    @Autowired
    JdbcTemplate jdbcTemplate;

    public UniversityAllocation getUniversityAllocationByName(String universityName) {
        List<UniversityAllocation> result = jdbcTemplate.query(SQL, UniversityAllocationRowMapper, universityName);
        return result.isEmpty() ? null : result.get(0);
    }

    public List<UniversityAllocation> getAllUniversityAllocations() {
        String UNIVERSITY_ALLOCATIONS_VIEW = "SELECT * FROM [dbo].[UniversityAllocationsView]";
        return jdbcTemplate.query(UNIVERSITY_ALLOCATIONS_VIEW, UniversityAllocationRowMapper);
    }

    public Integer allocateFundsToUniversity(int id, BigDecimal amount) {
        String ALLOCATE_STORE_PROCEDURE = "{CALL AllocateFundsToUniversity(?, ?)}";
        String GET_TOTAL_SPENT_PROCEDURE = "{CALL GetTotalAllocationByYear(?)}";
        String SELECT_ADMIN_BALANCE = "SELECT TotalAmount FROM BursaryDetails WHERE Year = YEAR(GETDATE())";

        BigDecimal adminBalance = jdbcTemplate.queryForObject(SELECT_ADMIN_BALANCE, BigDecimal.class);
        int currentYear = Year.now().getValue();
        BigDecimal totalAllocated = jdbcTemplate.queryForObject(GET_TOTAL_SPENT_PROCEDURE, BigDecimal.class,
                currentYear);
        BigDecimal remainingBalance = adminBalance.subtract(totalAllocated);

        try {
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Allocated amount must be greater than 0.");
            }
            if (amount.compareTo(remainingBalance) > 0) {
                throw new IllegalArgumentException("Insufficient balance, cannot allocate funds");
            }
            return jdbcTemplate.update(ALLOCATE_STORE_PROCEDURE, id, amount);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error allocating funds to university with ID: " + id, e);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred while allocating funds to university with ID: " + id,
                    e);
        }
    }

    public Integer allocateFundsToAllUniversities() {
        String COUNT_APPROVED_UNIVERSITIES = "SELECT COUNT(Status) FROM UniversityApplication WHERE Status = 'Approved'";
        String SELECT_ADMIN_BALANCE = "SELECT TotalAmount FROM BursaryDetails WHERE Year = YEAR(GETDATE())";
        String UPDATE_ALL_UNIVERSITY_ALLOCATIONS = "UPDATE UniversityAllocation " +
                "SET Amount = ? " +
                "WHERE UniversityID IN ( " +
                "    SELECT ua.UniversityID " +
                "    FROM UniversityApplication ua " +
                "    WHERE ua.Status = 'Approved' " +
                ")";

        try {

            BigDecimal availableBalance = jdbcTemplate.queryForObject(SELECT_ADMIN_BALANCE, BigDecimal.class);

            if (availableBalance.compareTo(BigDecimal.ZERO) == 0) {
                throw new RuntimeException("Admin balance is 0. No funds available for allocation.");
            }

            Integer numberOfApprovedUniversities = jdbcTemplate.queryForObject(COUNT_APPROVED_UNIVERSITIES,
                    Integer.class);

            BigDecimal amountPerUniversity = availableBalance.divide(BigDecimal.valueOf(numberOfApprovedUniversities),
                    4,
                    RoundingMode.HALF_UP);

            return jdbcTemplate.update(UPDATE_ALL_UNIVERSITY_ALLOCATIONS, amountPerUniversity);
        } catch (DataAccessException e) {

            throw new RuntimeException("Error allocating funds to all universities", e);
        } catch (RuntimeException e) {

            throw e;
        } catch (Exception e) {

            throw new RuntimeException("Unexpected error occurred while allocating funds to all universities", e);
        }
    }

    public Integer addNewAllocation(int universityId, BigDecimal amount, int bursaryDetails) {
        final String INSERT_NEW_ALLOCATION = "INSERT INTO UniversityAllocation (universityID,amount,BursaryDetailsID)" +
                "VALUES(?,?,?)";
        String SELECT_UNIVERSITY_STATUS = "SELECT Status FROM UniversityApplication  WHERE ID=?";
        String status = jdbcTemplate.queryForObject(SELECT_UNIVERSITY_STATUS, String.class, universityId);

        try {
            if (!"approved".equalsIgnoreCase(status)) {
                throw new IllegalStateException("University not approved for funding");
            }
            return jdbcTemplate.update(INSERT_NEW_ALLOCATION, universityId, amount, bursaryDetails);
        } catch (Exception e) {
            throw new RuntimeException("Error adding new allocation for university"); // more info
        }
    }

    private final RowMapper<UniversityAllocation> UniversityAllocationRowMapper = ((resultSet,
            rowNumber) -> {
        return new UniversityAllocation(resultSet.getString("universityName"),
                resultSet.getBigDecimal("amount"), resultSet.getInt("year"));
    });

    public BigDecimal getTotalSpentInYear(int year) {
        final String TOTAL_SPENT_PROCEDURE = "{CALL CalculateTotalAllocationForYear(?)}";
        try {
            return jdbcTemplate.queryForObject(TOTAL_SPENT_PROCEDURE, BigDecimal.class, year);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error retrieving total spent for year: " + year, e);
        }
    }
}
