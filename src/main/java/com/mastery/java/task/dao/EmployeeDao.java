package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class EmployeeDao implements Dao<Employee> {

  private static final String TABLE = "employee";
  @Autowired
  private JdbcTemplate jdbcTemplate;

  public Optional<Employee> getById(Long id) throws EmptyResultDataAccessException {
    String query = "Select*From " + TABLE + " WHERE employee_id = ?;";
    return Optional.of(jdbcTemplate.queryForObject(query, new EmployeeRowMapper(), id));
  }

  //retrieve the whole table as a List of Employee
  public List<Employee> getAll() {
    String query = "SELECT*FROM " + TABLE + ";";
    return jdbcTemplate.query(query, new EmployeeRowMapper());
  }

  //Adds a new employee to the db and returns its employeeID generated automatically by the db
  @Override
  public void save(Employee employee) {
    jdbcTemplate.update(con -> {
      PreparedStatement ps = con.prepareStatement("INSERT INTO " + TABLE + " ( "
          + "first_name, last_name, department_id, job_title, gender, date_of_birth"
          + ") VALUES ( ?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, employee.getFirstName());
      ps.setString(2, employee.getLastName());
      ps.setInt(3, employee.getDepartmentId());
      ps.setString(4, employee.getJobTitle());
      ps.setString(5, genderSQL(employee.getGender()));
      ps.setDate(6, Date.valueOf(employee.getDateOfBirth()));
      return ps;
    });
  }

  // updates the record with an employee_id of the employee
  @Override
  public void update(Employee employee) {
    jdbcTemplate.update(con -> {
      PreparedStatement ps = con.prepareStatement(
          "UPDATE " + TABLE + " SET first_name = ?," + " last_name = ?," + " department_id = ?,"
              + " job_title = ?," + " gender = ?," + " date_of_birth = ?"
              + " WHERE employee_id = ?;");
      ps.setString(1, employee.getFirstName());
      ps.setString(2, employee.getLastName());
      ps.setInt(3, employee.getDepartmentId());
      ps.setString(4, employee.getJobTitle());
      ps.setString(5, genderSQL(employee.getGender()));
      ps.setDate(6, Date.valueOf(employee.getDateOfBirth()));
      ps.setLong(7, employee.getEmployeeId());
      return ps;
    });
  }

  //removes a record by employee id.
  @Override
  public void deleteById(Long employeeId) {
    String query = "DELETE FROM " + TABLE + " WHERE employee_id = ?;";
    jdbcTemplate.update(query, employeeId);
  }

  private String genderSQL(Gender gender) {
    if (gender == Gender.MALE) {
      return "m";
    } else if (gender == Gender.FEMALE) {
      return "f";
    } else {
      return "n/b";
    }
  }

  //mapping for the "employee" table to retrieve Employee objects
  class EmployeeRowMapper implements RowMapper<Employee> {

    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
      Long employeeID = rs.getLong("employee_id");
      String firstName = rs.getString("first_name");
      String lastName = rs.getString("last_name");
      Integer departmentID = rs.getInt("department_id");
      String jobTitle = rs.getString("job_title");
      Gender gender;
      if (rs.getString("gender").equals("m")) {
        gender = Gender.MALE;
      } else if (rs.getString("gender").equals("f")) {
        gender = Gender.FEMALE;
      } else {
        gender = Gender.OTHER;
      }
      LocalDate dateOfBirth = rs.getDate("date_of_birth").toLocalDate();

      return new Employee(employeeID, firstName, lastName, departmentID, jobTitle, gender,
          dateOfBirth);
    }
  }
}
