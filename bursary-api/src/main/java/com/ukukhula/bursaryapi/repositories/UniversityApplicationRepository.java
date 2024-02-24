package com.ukukhula.bursaryapi.repositories;

import com.ukukhula.bursaryapi.entities.UniversityApplication;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UniversityApplicationRepository {

    final JdbcTemplate jdbcTemplate;

    public UniversityApplicationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UniversityApplication getApplicationByUniversityId(int universityId) throws Exception {
        String GET_APPLICATION_BY_UNIVERSITY_ID = "SELECT * FROM UniversityApplication WHERE UniversityID = ?";

        UniversityApplication result = jdbcTemplate.queryForObject(GET_APPLICATION_BY_UNIVERSITY_ID,
                universityApplicationRowMapper, universityId);
        if (result == null) {
            throw new EmptyResultDataAccessException("No application with that id", 1);
        }
        return result;

    }

    private final RowMapper<UniversityApplication> universityApplicationRowMapper = ((resultSet, rowNumber) -> {
        return new UniversityApplication(resultSet.getInt("ID"),
                resultSet.getInt("UniversityID"), resultSet.getString(
                        "Motivation"),
                resultSet.getString("StatusId"),
                resultSet.getString("ReviewerComment"));
    });
}
