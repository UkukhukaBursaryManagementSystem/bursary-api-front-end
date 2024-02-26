package com.ukukhula.bursaryapi.repositories;

import com.ukukhula.bursaryapi.entities.StudentApplication;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class StudentApplicationRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public StudentApplicationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
