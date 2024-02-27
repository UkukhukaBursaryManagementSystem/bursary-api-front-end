
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

    @Autowired
    JdbcTemplate jdbcTemplate;
    final String SELECT_AVAILABLE_ADMIN_BALANCE = "SELECT dbo.CalculateRemainingAdminBalance(?)";

    public UniversityAllocation getUniversityAllocationByName(String universityName) {
        final String VIEW_ALLOCATIONS = "SELECT * FROM [dbo].[UniversityAllocationsView] WHERE UniversityName = ?";
        List<UniversityAllocation> result = jdbcTemplate.query(VIEW_ALLOCATIONS, UniversityAllocationRowMapper,
                universityName);
        return result.isEmpty() ? null : result.get(0);
    }

    public List<UniversityAllocation> getAllUniversityAllocations() {
        String UNIVERSITY_ALLOCATIONS_VIEW = "SELECT * FROM [dbo].[UniversityAllocationsView]";
        return jdbcTemplate.query(UNIVERSITY_ALLOCATIONS_VIEW, UniversityAllocationRowMapper);
    }

    public Integer allocateFundsToAllUniversities() {
        String COUNT_APPROVED_UNIVERSITIES = "SELECT dbo.GetApprovedUniversityCount()";
        String ALLOCATE_FUNDS_EVENLY = "{CALL InsertUniversityAllocations(?)}";
  

        Year year = Year.now();
        int intYear = year.getValue();

        Integer numberOfApprovedUniversities = jdbcTemplate.queryForObject(COUNT_APPROVED_UNIVERSITIES, Integer.class);
        BigDecimal availableBalance = jdbcTemplate.queryForObject(SELECT_AVAILABLE_ADMIN_BALANCE, BigDecimal.class,
                intYear);
        try {
            if (numberOfApprovedUniversities == 0) {
                throw new IllegalStateException("No universities approved for funding yet");
            }

            if (availableBalance.compareTo(BigDecimal.ZERO) == 0) {
                throw new IllegalStateException("Admin balance is 0. No funds available for allocation.");
            }
            return jdbcTemplate.update(ALLOCATE_FUNDS_EVENLY,2024);
        } catch (RuntimeException e) {

            throw e;
        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }

    public Integer addNewAllocation(String universityName, BigDecimal amount, int year) {
        final String INSERT_NEW_ALLOCATION = "{CALL InsertUniversityAllocation(?,?,?)}";

        String SELECT_UNIVERSITY_STATUS = "SELECT dbo.GetUniversityApplicationStatusByUniversityName(?)";
        String applicationStatus = jdbcTemplate.queryForObject(SELECT_UNIVERSITY_STATUS, String.class, universityName);

        BigDecimal availableAdminBalance = jdbcTemplate.queryForObject(SELECT_AVAILABLE_ADMIN_BALANCE, BigDecimal.class,
                year);

        try {
            if (amount.compareTo(availableAdminBalance) > 0) {
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
