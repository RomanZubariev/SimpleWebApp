package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

  private static final String GET_BY_ID_SQL = "SELECT * FROM employee WHERE employee_id = ?;";
  private static final String GET_ALL_SQL = "SELECT * FROM employee;";
  private static final String DELETE_BY_ID_SQL = "DELETE FROM employee WHERE employee_id = ?;";
  private static final String UPDATE_SQL = "UPDATE employee SET first_name = ?, last_name = ?,"
      + " department_id = ?, job_title = ?, gender = ?, date_of_birth = ? WHERE employee_id = ?;";

  private final JdbcTemplate jdbcTemplate;
  private final SimpleJdbcInsert simpleJdbcInsert;
  private final BeanPropertyRowMapper<Employee> beanPropertyRowMapper;

  @Autowired
  public EmployeeDaoImpl(JdbcTemplate jdbcTemplate, SimpleJdbcInsert simpleJdbcInsert) {
    this.jdbcTemplate = jdbcTemplate;
    this.simpleJdbcInsert = simpleJdbcInsert.withTableName("employee")
        .usingGeneratedKeyColumns("employee_id");
    this.beanPropertyRowMapper = new BeanPropertyRowMapper<>(Employee.class);
  }

  public Employee getById(Long id) throws EmptyResultDataAccessException {
    return jdbcTemplate.queryForObject(GET_BY_ID_SQL, beanPropertyRowMapper, id);
  }

  //read the whole table as a List of Employee
  public List<Employee> getAll() {
    return jdbcTemplate.query(GET_ALL_SQL, beanPropertyRowMapper);
  }

  //Adds a new employee to the db and returns its employeeID generated automatically by the db
  @Override
  public Employee save(Employee employee) {
    Map<String, Object> parameters = new HashMap<>(6);
    parameters.put("first_name", employee.getFirstName());
    parameters.put("last_name", employee.getLastName());
    parameters.put("department_id", employee.getDepartmentId());
    parameters.put("job_title", employee.getJobTitle());
    parameters.put("gender", employee.getGender().toString());
    parameters.put("date_of_birth", Date.valueOf(employee.getDateOfBirth()));
    employee.setEmployeeId((Long) simpleJdbcInsert.executeAndReturnKey(parameters));
    return employee;
  }

  // updates the record with an employee_id of the employee
  @Override
  public Employee update(Employee employee) {
    int numOfRowsUpdated = jdbcTemplate.update(UPDATE_SQL, employee.getFirstName(),
        employee.getLastName(), employee.getDepartmentId(), employee.getJobTitle(),
        employee.getGender().toString(), Date.valueOf(employee.getDateOfBirth()),
        employee.getEmployeeId());
    if (numOfRowsUpdated == 0) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
    }
    return employee;
  }

  //removes a record by employee id.
  @Override
  public void deleteById(Long employeeId) {
    jdbcTemplate.update(DELETE_BY_ID_SQL, employeeId);
  }
}
