package com.ukukhula.bursaryapi.repositories;

import com.ukukhula.bursaryapi.dto.NewStudentApplicationDTO;
import com.ukukhula.bursaryapi.entities.StudentApplication;

import java.sql.SQLException;
import java.util.List;

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

    public int insertStudentApplication(NewStudentApplicationDTO application) throws SQLException
    {
        return  jdbcTemplate.update(INSERT_STUDENT_APPLICATION,
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
        application.getFundingYear()
    );
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

    public List<StudentApplication> getAllStudentsApplications() {
        final String SQL = "SELECT * FROM StudentApplication";

        List<StudentApplication> students = jdbcTemplate.query(SQL, studentRowMapper);
        return students;
    }

    public Integer updateStudentsApplicationStatus(int studentID, String status) {
        final String SQL = "UPDATE StudentApplication SET Status = ? WHERE StudentID = ?";

        return jdbcTemplate.update(SQL, status, studentID);
    }

    public Integer updateStudentsApplicationColumnValue(int studentID, String columnName, String value) {

        final String SQL = "UPDATE StudentApplication SET " + columnName + " = ? WHERE StudentID = ?";

        return jdbcTemplate.update(SQL, value, studentID);
    }




}
