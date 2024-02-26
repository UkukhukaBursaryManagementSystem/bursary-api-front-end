
package com.ukukhula.bursaryapi.repositories;

import com.ukukhula.bursaryapi.entities.UniversityAllocation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Year;
import java.util.List;
import java.util.Map;

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

    public Integer addNewAllocation(String universityName, BigDecimal amount, int year) {
        final String INSERT_NEW_ALLOCATION = "{CALL InsertUniversityAllocation(?,?,?)}";

        String SELECT_UNIVERSITY_STATUS = "SELECT dbo.GetUniversityApplicationStatusByUniversityName(?)";
        String applicationStatus = jdbcTemplate.queryForObject(SELECT_UNIVERSITY_STATUS, String.class, universityName);

        String SELECT_ADMIN_BALANCE = "SELECT dbo.GetAdminBalanceForYear(?)";
        BigDecimal adminBalance = jdbcTemplate.queryForObject(SELECT_ADMIN_BALANCE, BigDecimal.class, year);

        String GET_TOTAL_SPENT = "{CALL GetTotalUniversityAllocations(?)}";
        BigDecimal totalSpent = jdbcTemplate.queryForObject(GET_TOTAL_SPENT, BigDecimal.class, year);
        BigDecimal remainingBalance = adminBalance.subtract(totalSpent);

        try {
            if (amount.compareTo(adminBalance) > 0 || amount.compareTo(remainingBalance) > 0) {
                throw new IllegalStateException("Insufficient admin balance");
            }

            if (!"approved".equalsIgnoreCase(applicationStatus)) {
                throw new IllegalStateException("University not approved for funding");
            }
            return jdbcTemplate.update(INSERT_NEW_ALLOCATION, universityName, amount, year);
        } catch (Exception e) {
            throw new RuntimeException(e);
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
