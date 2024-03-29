package com.ukukhula.bursaryapi.repositories;

import com.ukukhula.bursaryapi.entities.UserRole;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRoleRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRoleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getByRoleId(int roleId) {
        String GET_ROLE = "SELECT Role FROM UserRole WHERE ID = ?";
        UserRole userRole = (jdbcTemplate.queryForObject(GET_ROLE,
                userRoleRowMapper,
                roleId));

        return userRole.getRole();
    }

    public String getUserRole(int userId) {
        String GET_USER_ROLE = "SELECT UserRole FROM UserWithRoles WHERE ID =?";
        return jdbcTemplate.queryForObject(GET_USER_ROLE, String.class, userId);
    }

    private final RowMapper<UserRole> userRoleRowMapper = ((resultSet,
            rowNumber) -> new UserRole(resultSet.getInt("ID"), resultSet.getString(
                    "Role")));
}
