package com.ukukhula.bursaryapi.repositories;

import com.ukukhula.bursaryapi.dto.NewStudentApplicationDTO;
import com.ukukhula.bursaryapi.dto.StudentApplicationDTO;
import com.ukukhula.bursaryapi.entities.StudentApplication;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class StudentApplicationRepository {

    private static final String INSERT_STUDENT_APPLICATION = "{CALL " +
            "uspCreateStudentWithApplication(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

    @Autowired
    JdbcTemplate jdbcTemplate;

    public StudentApplicationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int insertStudentApplication(NewStudentApplicationDTO application) throws SQLException {
        return jdbcTemplate.update(INSERT_STUDENT_APPLICATION,
                application.getFirstName(),
                application.getLastName(),
                application.getIDNumber(),
                application.getPhoneNumber(),
                application.getEmail(),
                application.getCourseOfStudy(),
                application.getGenderID(),
                application.getEthnicityID(),
                application.getDepartmentID(),
                application.getUniversityID(),
                application.getApplicationMotivation(),
                application.getBursaryAmount(),
                application.getHeadOfDepartmentID(),
                application.getFundingYear());
    }

    public List<StudentApplicationDTO> getAllStudentApplicationsDTO() {
        String sql = "SELECT  " +
                "ApplicationID,FirstName, LastName, IDNumber, GenderIdentity, Ethnicity, " +
                "UniversityName, CourseOfStudy, ReviewerComment, Department, PhoneNumber,Status,HODName,FundingYear, BursaryAmount,Motivation,HeadOfDepartmentID, Email "
                +
                "FROM dbo.vStudentApplications";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            StudentApplicationDTO studentInfo = new StudentApplicationDTO(
                    rs.getLong("ApplicationID"),
                    rs.getString("FirstName"),
                    rs.getString("LastName"),
                    rs.getString("IDNumber"),
                    rs.getString("GenderIdentity"),
                    rs.getString("Ethnicity"),
                    rs.getString("PhoneNumber"),
                    rs.getString("Email"),
                    rs.getString("UniversityName"),
                    rs.getString("department"),
                    rs.getString("CourseOfStudy"),
                    rs.getString("ReviewerComment"),
                    rs.getString("Motivation"),
                    rs.getBigDecimal("BursaryAmount"),
                    rs.getInt("FundingYear"),
                    rs.getString("Status"),
                    rs.getLong("HeadOfDepartmentID"),
                    rs.getString("HODName"));

            return studentInfo;
        });
    }

    private final RowMapper<StudentApplication> studentRowMapper = ((resultSet,
            rowNumber) -> {
        return new StudentApplication(resultSet.getInt("ID"),
                resultSet.getInt("StudentID"),
                resultSet.getString("Motivation"),
                resultSet.getBigDecimal("BursaryAmount"),
                resultSet.getString("StatusId"),
                resultSet.getString("ReviewerComment"),
                resultSet.getDate("Date"));
    });

    public StudentApplication findByStudentID(int studentID) {
        String SQL = "SELECT * FROM StudentApplication WHERE StudentID = ?";

        StudentApplication students = jdbcTemplate.queryForObject(SQL, studentRowMapper, studentID);
        return students;
    }

    public Integer updateApplicationDetails(
            String firstName, String lastName, String idNumber,
            String gender, String phoneNumber, String email, String ethnicity, String courseOfStudy,
            String departmentName, String reviewerComment, String motivation, BigDecimal requestedAmount,
            String applicationStatus) {
        String UPDATE_APPLICATION_DETAILS = "{CALL UpdateStudentDetails(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        System.out.println(
                "\nFirst Name: " + (firstName instanceof String )+
                        "\nLast Name: " + (lastName instanceof String) +
                        "\nID Number: " + (idNumber instanceof String ) +
                        "\nGender: " + (gender instanceof String) +
                        "\nPhone Number: " + (phoneNumber instanceof String) +
                        "\nEmail: " + email +
                        "\nEthnicity: " + ethnicity +
                        "\nCourse of Study: " + (courseOfStudy instanceof String) +
                        "\nDepartment Name: " + departmentName +
                        "\nReviewer Comment: " + reviewerComment +
                        "\nMotivation: " + (motivation instanceof String) +
                        "\nRequested Amount: " + requestedAmount +
                        "\nApplication Status: " + applicationStatus);
        Integer result = jdbcTemplate.update(UPDATE_APPLICATION_DETAILS, Integer.class, firstName,
                lastName, idNumber,
                gender, phoneNumber, email, ethnicity, courseOfStudy,
                departmentName, reviewerComment, motivation, requestedAmount,
                applicationStatus);
        return result;
    }

    // public interface StudentApplicationRepository extends
    // CrudRepository<StudentApplication, Integer> {
    // Integer updateStudentDetails(int applicationID, String firstName, String
    // lastName, String idNumber,
    // String gender, String phoneNumber, String email, String ethnicity, String
    // courseOfStudy,
    // String departmentName, String reviewerComment, String motivation, BigDecimal
    // requestedAmount,
    // Integer fundingYear, String applicationStatus);
    // }

    public List<StudentApplication> getAllStudentsApplications() {
        final String SQL = "SELECT * FROM StudentApplication";

        List<StudentApplication> students = jdbcTemplate.query(SQL, studentRowMapper);
        return students;
    }

    public Integer updateStudentsApplicationStatus(int studentID, String status) {
        final String SQL = "UPDATE StudentApplication SET Status = ? WHERE StudentID = ?";

        return jdbcTemplate.update(SQL, status, studentID);
    }

    private final RowMapper<StudentApplicationDTO> studentApplicationDTOMapper = new RowMapper<StudentApplicationDTO>() {
        @Override
        public StudentApplicationDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            StudentApplicationDTO dto = new StudentApplicationDTO(
                    rs.getLong("ApplicationID"),
                    rs.getString("FirstName"),
                    rs.getString("LastName"),
                    rs.getString("IDNumber"),
                    rs.getString("GenderIdentity"),
                    rs.getString("Ethnicity"),
                    rs.getString("PhoneNumber"),
                    rs.getString("Email"),
                    rs.getString("UniversityName"),
                    rs.getString("department"),
                    rs.getString("CourseOfStudy"),
                    rs.getString("ReviewerComment"),
                    rs.getString("Motivation"),
                    rs.getBigDecimal("BursaryAmount"),
                    rs.getInt("FundingYear"),
                    rs.getString("Status"),
                    rs.getLong("HeadOfDepartmentID"),
                    rs.getString("HODName"));

            return dto;
        }
    };

    public List<StudentApplicationDTO> findByHODName(String HODName) {
        String SQL = "SELECT ApplicationID, FirstName, LastName, IDNumber, GenderIdentity, Ethnicity, PhoneNumber, Email, UniversityName, department, CourseOfStudy, ReviewerComment, Motivation, BursaryAmount, FundingYear, Status, HeadOfDepartmentID, HODName FROM vStudentApplications WHERE HODName = ?";
        List<StudentApplicationDTO> studentApplications = jdbcTemplate.query(SQL, studentApplicationDTOMapper, HODName);
        return studentApplications;
    }

    public Integer updateStudentsApplicationColumnValue(int studentID, String columnName, String value) {

        final String SQL = "UPDATE StudentApplication SET " + columnName + " = ? WHERE StudentID = ?";

        return jdbcTemplate.update(SQL, value, studentID);
    }

    public Integer deleteStudentApplication(int studentId) {
        String DELETE_STUDENT_APPLICATION = "DELETE FROM StudentApplication WHERE ID = ?";
        String FIND_STUDENT_BY_ID = "SELECT COUNT(*) FROM StudentApplication WHERE ID = ?";

        int numberOfRows = jdbcTemplate.queryForObject(FIND_STUDENT_BY_ID, Integer.class, studentId);

        if (numberOfRows == 0) {
            throw new IllegalArgumentException("No student with the given ID: " + studentId);
        }

        return jdbcTemplate.update(DELETE_STUDENT_APPLICATION, studentId);
    }

}
