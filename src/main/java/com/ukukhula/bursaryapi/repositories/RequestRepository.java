package com.ukukhula.bursaryapi.repositories;

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
    public RequestRepository(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Request> requestRowMapper = ((resultSet, rowNumber) -> {
        return new Request(
                resultSet.getString("firstName"),
                resultSet.getString("lastName"),
                resultSet.getString("phoneNumber"),
                resultSet.getString("email"),
                resultSet.getString("department"),
                resultSet.getString("university")
        );
    });

    public Request createRequest(Request request) {

        String SEND_REQUEST = "INSERT INTO [dbo].[Requests]" +
                " ([firstName], [lastName], [phoneNumber], [email], [department], [university])" +
                " VALUES(?, ?, ?, ?, ?, ?)";

        int rowsAffected = jdbcTemplate.update(SEND_REQUEST,
                request.getFirstName(),
                request.getLastName(),
                request.getPhoneNumber(),
                request.getEmail(),
                request.getDepartment(),
                request.getUniversityName());


        if (rowsAffected > 0) {
            return request;
        } else {
            throw new RuntimeException("Error creating Request. No rows affected.");
        }
    }
}
