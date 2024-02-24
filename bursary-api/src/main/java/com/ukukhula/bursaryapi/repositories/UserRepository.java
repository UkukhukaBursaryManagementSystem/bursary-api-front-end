package com.ukukhula.bursaryapi.repositories;

import com.ukukhula.bursaryapi.entities.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<User> getUserByEmail(String email) {
        String GET_USER_BY_EMAIL = "SELECT * FROM [dbo].[User] LEFT JOIN " +
                "Contact" +
                " " +
                "ON [dbo].[User].ContactID = Contact.ID WHERE Contact.Email =" +
                " ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(GET_USER_BY_EMAIL,
                userRowMapper, 1));
    }

    public Boolean doesUserExist(String email) {
        return null;
    }

    private final RowMapper<User> userRowMapper = ((resultSet, rowNumber) -> new User(resultSet.getInt("ID"),
            resultSet.getString("firstName"),
            resultSet.getString("lastName"),
            resultSet.getInt("ContactID"), resultSet.getInt("UserRoleID"), resultSet.getBoolean("IsUserActive")));
}
