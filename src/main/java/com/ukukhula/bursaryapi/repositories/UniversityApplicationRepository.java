package com.ukukhula.bursaryapi.repositories;

import com.ukukhula.bursaryapi.entities.UniversityApplication;
import java.util.List;

import org.springframework.dao.DataAccessException;
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

    public Integer addUniversityApplicationByAdmin(String universityName) {
        String INSERT_UNIVERSITY = "INSERT INTO University (UniversityName) VALUES (?)";
        String ADD_APPLICATION = "{CALL dbo.CreateAdminUniversityApplication(?)}";
        jdbcTemplate.update(INSERT_UNIVERSITY, universityName);
        int rowsAffected = jdbcTemplate.update(ADD_APPLICATION, universityName);
        if (rowsAffected == 0) {
            throw new DataAccessException("Failed to insert university: " + universityName) {
            };
        }
        return rowsAffected;
    }

    public List<UniversityApplication> getAllUniversityApplications() throws Exception {
        String GET_ALL_APPLICATIONS = "SELECT * FROM vw_UniversityApplications";

        List<UniversityApplication> results = jdbcTemplate.query(GET_ALL_APPLICATIONS,
                universityApplicationRowMapper);
        return results;
    }

    public List<UniversityApplication> getAllApprovedUniversityApplications() throws Exception {
        String GET_ALL_APPLICATIONS = "SELECT * FROM [dbo].[UniversityApplication] WHERE statusID = 2";

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
