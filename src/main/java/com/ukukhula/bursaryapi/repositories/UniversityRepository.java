package com.ukukhula.bursaryapi.repositories;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.Year;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Repository;

import com.ukukhula.bursaryapi.dto.UniversityInfoDTO;
import com.ukukhula.bursaryapi.entities.Department;
import com.ukukhula.bursaryapi.entities.Ethnicity;
import com.ukukhula.bursaryapi.entities.Gender;
import com.ukukhula.bursaryapi.entities.HeadOfDepartment;
import com.ukukhula.bursaryapi.entities.University;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

@Repository
public class UniversityRepository {

  private static final String INSERT_UNIVERSITY = "{CALL " +
      "uspAddUniversityByName(?)}";
  private static final String GET_UNIVERSITY_BY_ID = "{CALL " +
      "uspGetUniversityById(?)}";
  private static final String GET_ALL_UNIVERSITIES = "SELECT ID, UniversityName FROM University";

  private static final String GET_UNIVERSITIES_BY_STATUS ="SELECT " +
          "UniversityApplication.UniversityID AS ID," +
          "University.UniversityName " +
          "FROM " +
          "UniversityApplication " +
          "JOIN " +
          "University ON UniversityApplication.UniversityID = University.ID " +
          "WHERE UniversityApplication.StatusID = ?";
  private static final String GET_ALL_DEPARTMENTS = "SELECT ID, Name FROM Department";

  private static final String GET_ALL_HODs = "SELECT ID, Name from vHeadOFDepartments";

  private static final String GET_ALL_GENDERS = "SELECT  ID,[Identity] as [Name] FROM Gender";

  private static final String GET_ALL_ETHNICITY = "SELECT  ID,[Ethnic] as Name FROM Ethnicity";

  private static final String GET_UNIVERSITY_INFO_FROM_USER_ID = "SELECT Rep.ID, Rep.DepartmentID, Rep.UniversityID , uni.UniversityName FROM UniversityRepresentative AS Rep INNER JOIN University as uni ON rep.UniversityID = uni.ID WHERE [UserID] = ?";

  final JdbcTemplate jdbcTemplate;

  public UniversityRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public Integer addUniversity(String name) {
    try {
      KeyHolder keyHolder = new GeneratedKeyHolder();

      jdbcTemplate.update(
          connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_UNIVERSITY,
                Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);

            return ps;
          }, keyHolder);

      return Objects.requireNonNull(keyHolder.getKey()).intValue();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public University getUniversityById(int id) {
    try {
      return jdbcTemplate.queryForObject(GET_UNIVERSITY_BY_ID, universityRowMapper, id);
    } catch (EmptyResultDataAccessException e) {
      throw new RuntimeException("University not found with ID: " + id, e);
    } catch (Exception e) {
      throw new RuntimeException("Unexpected error occurred");
    }
  }


  public UniversityInfoDTO getUniversityInfoByUserID(int id) {
    try {
      return jdbcTemplate.queryForObject(GET_UNIVERSITY_INFO_FROM_USER_ID, infoRowMapper, id);
    } catch (EmptyResultDataAccessException e) {
      throw new RuntimeException("User not found with ID: " + id, e);
    } catch (Exception e) {
      throw new RuntimeException("Unexpected error occurred");
    }
  }

  public BigDecimal getUniversityBalance(String universityName) {
    final String GET_UNIVERSITY_BALANCE = "SELECT DBO.GetRemainingUniversityFunds(? ,?)";
    int currentYear = Year.now().getValue();
    return jdbcTemplate.queryForObject(GET_UNIVERSITY_BALANCE, BigDecimal.class, universityName, currentYear);

  }

  public BigDecimal getUniversityTotalSpent(String universityName) {
    final String GET_UNIVERSITY_TOTAL_SPENT = "SELECT DBO.GetTotalUniversityAllocatedToStudents(? ,?)";
    int currentYear = Year.now().getValue();
    return jdbcTemplate.queryForObject(GET_UNIVERSITY_TOTAL_SPENT, BigDecimal.class, universityName, currentYear);

  }

  public List<University> getAllUniversities() {
    try {
      return jdbcTemplate.query(GET_ALL_UNIVERSITIES, universityRowMapper);
    } catch (EmptyResultDataAccessException e) {
      throw new RuntimeException("No university allocations to show");
    } catch (Exception e) {
      throw new RuntimeException("Unexpected error occurred", e);
    }
  }

  public List<Department> getAllDepartments() {
    try {
      return jdbcTemplate.query(GET_ALL_DEPARTMENTS, departmentRowMapper);
    } catch (EmptyResultDataAccessException e) {
      throw new RuntimeException("No departements to show");
    } catch (Exception e) {
      throw new RuntimeException("Unexpected error occurred", e);
    }
  }

  public List<HeadOfDepartment> getAllHeadOfDepartments() {
    try {
      return jdbcTemplate.query(GET_ALL_HODs, headDepartmentRowMapper);
    } catch (EmptyResultDataAccessException e) {
      throw new RuntimeException("No head of  departements to show");
    } catch (Exception e) {
      throw new RuntimeException("Unexpected error occurred", e);
    }
  }

  public List<Gender> getAllGenders() {
    try {
      return jdbcTemplate.query(GET_ALL_GENDERS, genderRowMapper);
    } catch (EmptyResultDataAccessException e) {
      throw new RuntimeException("No head of genders to show");
    } catch (Exception e) {
      throw new RuntimeException("Unexpected error occurred", e);
    }
  }

  public List<Ethnicity> getAllEthnicities() {
    try {
      return jdbcTemplate.query(GET_ALL_ETHNICITY, ethnicityRowMapper);
    } catch (EmptyResultDataAccessException e) {
      throw new RuntimeException("No head of  ethnicities to show");
    } catch (Exception e) {
      throw new RuntimeException("Unexpected error occurred", e);
    }
  }


  public String getUniversityForHOD(int id) {
    final String SELECT_UNIVERSITY = "SELECT UniversityName FROM HODUniversityView WHERE UserID = ?";
    return jdbcTemplate.queryForObject(SELECT_UNIVERSITY, String.class, id);
  }

  public List<University> getUniversitiesByApplicationStatus(int status)
  {
    try {
      return jdbcTemplate.query(GET_UNIVERSITIES_BY_STATUS, universityRowMapper, status);
    } catch (EmptyResultDataAccessException e) {
      throw new RuntimeException("No University found with status" + status);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


  private final RowMapper<University> universityRowMapper = ((resultSet,
      rowNumber) -> new University(resultSet.getInt("ID"),
          resultSet.getString("UniversityName")));

  private final RowMapper<Department> departmentRowMapper = ((resultSet,
      rowNumber) -> new Department(resultSet.getInt("ID"),
          resultSet.getString("Name")));

  private final RowMapper<HeadOfDepartment> headDepartmentRowMapper = ((resultSet,
      rowNumber) -> new HeadOfDepartment(resultSet.getInt("ID"),
          resultSet.getString("Name")));

  private final RowMapper<Gender> genderRowMapper = ((resultSet,
      rowNumber) -> new Gender(resultSet.getInt("ID"),
          resultSet.getString("Name")));

  private final RowMapper<Ethnicity> ethnicityRowMapper = ((resultSet,
      rowNumber) -> new Ethnicity(resultSet.getInt("ID"),
          resultSet.getString("Name")));



  private final RowMapper<UniversityInfoDTO> infoRowMapper = ((resultSet,
  rowNumber) -> new UniversityInfoDTO(resultSet.getInt("ID"),
      resultSet.getInt("DepartmentID"), resultSet.getInt("UniversityID"), resultSet.getString("UniversityName")));
        
}
