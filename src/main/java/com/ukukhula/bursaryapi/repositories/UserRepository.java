package com.ukukhula.bursaryapi.repositories;

import com.ukukhula.bursaryapi.dto.AdminDTO;
import com.ukukhula.bursaryapi.dto.DocumentsDTO;
import com.ukukhula.bursaryapi.dto.HodDTO;
import com.ukukhula.bursaryapi.dto.UserLogDTO;
import com.ukukhula.bursaryapi.entities.Request;
import com.ukukhula.bursaryapi.entities.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
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
                "[User].ID, " +
                "Contact.Email, " +
                "[UserRole].[Role] " +
                "FROM " +
                "[User] " +
                "JOIN Contact ON [User].ContactID = Contact.ID " +
                "JOIN UserRole ON [User].[UserRoleID] = UserRole.ID " +
                "WHERE Contact.Email = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(GET_USER_BY_EMAIL, userRowMapper, email)).get();
    }

    public Integer addHod(String firstName, String lastName, String phoneNumber, String email, String department,
            String universityName)  {
        String INSERT_INTO_REQUESTS = "{CALL [dbo].[InsertHODByAdmin] (?,?,?,?,?,?)}";
        return jdbcTemplate.update(INSERT_INTO_REQUESTS, firstName, lastName, phoneNumber, email,
                department, universityName);

    }

    public Integer createRequest(String firstName, String lastName, String phoneNumber, String email, String department,
            String universityName) {
        String INSERT_INTO_REQUESTS = "{CALL InsertRequest (?,?,?,?,?,?)}";
        return jdbcTemplate.update(INSERT_INTO_REQUESTS, firstName, lastName, phoneNumber, email,
                department, universityName);

    }

    public void addAdmin(AdminDTO admin) throws Exception {

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("uspInsertAdmin");
        MapSqlParameterSource inParams = new MapSqlParameterSource()
                .addValue("FirstName", admin.getFirstName())
                .addValue("LastName", admin.getLastName())
                .addValue("PhoneNumber", admin.getPhoneNumber())
                .addValue("Email", admin.getEmail());
        simpleJdbcCall.execute(inParams);

    }

    public UserLogDTO addUserLog(UserLogDTO userLogDTO) {

        String INSERT_LOG = "INSERT INTO [dbo].[UserLogs]" +
                " ([UserID], [UserName], [MsToken], [TimeStamp])" +
                " VALUES(?, ?, ?, DEFAULT)";

        int rowsAffected = jdbcTemplate.update(INSERT_LOG,
                userLogDTO.getUserId(),
                userLogDTO.getUserName(),
                userLogDTO.getUserAccessToken());

        if (rowsAffected > 0) {
            return userLogDTO;
        } else {
            throw new RuntimeException("Error creating Request. No rows affected.");
        }
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
            resultSet.getInt("ID")));

    private final RowMapper<UserLogDTO> userLogRowMapper = ((resultSet, rowNumber) -> new UserLogDTO(
            resultSet.getInt("userId"),
            resultSet.getString("userName"),
            resultSet.getString("userAccessToken")));
}
