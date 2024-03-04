package com.ukukhula.bursaryapi.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.ukukhula.bursaryapi.entities.Request;

@Repository
public class RequestRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    public RequestRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Request> requestRowMapper = ((resultSet, rowNumber) -> {
        return new Request(
                resultSet.getString("firstName"),
                resultSet.getString("lastName"),
                resultSet.getString("phoneNumber"),
                resultSet.getString("email"),
                resultSet.getString("department"),
                resultSet.getString("universityName"));
    });

    public Integer createRequest(String firstName, String lastName, String phoneNumber, String email, String department,
            String universityName) {
        String INSERT_INTO_REQUESTS = "{CALL InsertRequest (?,?,?,?,?,?)}";
        return jdbcTemplate.update(INSERT_INTO_REQUESTS, firstName, lastName, phoneNumber, email,
                department,universityName);

    }

    public List<Request> getAllRequests() {
        String GET_ALL_REQUESTS = "SELECT * FROM [dbo].[Requests]";
        return jdbcTemplate.query(GET_ALL_REQUESTS, requestRowMapper);
    }

}
