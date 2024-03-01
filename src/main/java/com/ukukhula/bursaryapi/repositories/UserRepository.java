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

    public User getUserByEmail(String email) {
        String GET_USER_BY_EMAIL = "SELECT " +
                    "[User].FirstName, " +
                    "[User].LastName, " +
                    "[User].IsUserActive," +
                    "[User].ID," +
                    "Contact.Email, " +
                    "[UserRole].[Role] " +
                    "FROM " +
                    "[User] " +
                    "JOIN Contact ON [User].ContactID = Contact.ID " +
                    "JOIN UserRole ON [User].[UserRoleID] = UserRole.ID " +
                    "WHERE Contact.Email = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(GET_USER_BY_EMAIL, userRowMapper, email)).get();
    }

    public Boolean doesUserExist(String email) {
        return null;
    }

    private final RowMapper<User> userRowMapper = ((resultSet, rowNumber) -> new User(
            resultSet.getString("firstName"),
            resultSet.getString("lastName"),
            resultSet.getString("email"),
            resultSet.getString("role"),
            resultSet.getBoolean("IsUserActive"),
            resultSet.getInt("ID")
    ));
}
