package com.ukukhula.bursaryapi.repositories;

import com.ukukhula.bursaryapi.entities.UniversityAllocation;
import com.ukukhula.bursaryapi.entities.UniversityApplication;

import java.util.List;

import org.springframework.dao.DataAccessException;
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

    public UniversityApplication getApplicationByName(String universityName) throws Exception {

        String GET_APPLICATION_BY_UNIVERSITY_ID = "SELECT * FROM vw_UniversityApplications WHERE UniversityName = ?";
        ;
        UniversityApplication result = jdbcTemplate.queryForObject(GET_APPLICATION_BY_UNIVERSITY_ID,
                universityApplicationRowMapper, universityName);

        return result;

    }

    public List<UniversityApplication> getAllUniversityApplications() throws Exception {
        String GET_ALL_APPLICATIONS = "SELECT * FROM vw_UniversityApplications";

        List<UniversityApplication> results = jdbcTemplate.query(GET_ALL_APPLICATIONS,
                universityApplicationRowMapper);
        return results;
    }

    private final RowMapper<UniversityApplication> universityApplicationRowMapper = ((resultSet, rowNumber) -> {
        return new UniversityApplication(
                resultSet.getInt("ApplicationID"), resultSet.getString(
                        "UniversityName"),
                resultSet.getString("Motivation"),
                resultSet.getString("ApplicationStatus"),
                resultSet.getString("ReviewerComment")

        );
    });

}
