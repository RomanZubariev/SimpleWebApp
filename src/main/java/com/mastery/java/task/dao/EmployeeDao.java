package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class EmployeeDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String table="employee";
    //sequence manages autoincrement in the employee_id column
    private static final String sequence = "my_serial";

    public Employee retrieve(Long id) throws EmptyResultDataAccessException {
        String query = "Select*From "+table+ " WHERE employee_id='"+id+"';";
        return jdbcTemplate.queryForObject(query,new EmployeeRowMapper());
    }
    public List<Employee> findByName(String firstName){
        String query = "Select*From "+table+ " WHERE first_name = '"+firstName+"';";
        return jdbcTemplate.query(query, new EmployeeRowMapper());
    }
    public List<Employee> findBySurname(String lastName){
        String query = "Select*From "+table+ " WHERE last_name = '"+lastName+"';";
        return jdbcTemplate.query(query, new EmployeeRowMapper());
    }
    //retrieve the whole table as a List of Employee
    public List<Employee> retrieveAll(){
        String query = "SELECT*FROM "+table+";";
        return jdbcTemplate.query(query, new EmployeeRowMapper());
    }
    //Adds a new employee to the db and returns its employeeID generated automatically by the db
    public Long create (Employee employee) throws DuplicateKeyException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement("INSERT INTO "+table+" ( "+
                    "first_name, last_name, department_id, job_title, gender, date_of_birth"+
                    ") VALUES ( ?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,employee.getFirstName());
            ps.setString(2,employee.getLastName());
            ps.setInt(3, employee.getDepartmentID());
            ps.setString(4, employee.getJobTitle());
            ps.setString(5, genderSQL(employee.getGender()));
            ps.setDate(6,new Date(employee.getDateOfBirth().getTime().getTime()));
            return ps;
        }, keyHolder);

        return ((Number) keyHolder.getKeys().get("employee_id")).longValue();
    }
    // updates the record with an employee_id of the employee
    public Integer update (Employee employee){
        return jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement("UPDATE " + table+
                    " SET first_name = ?," +
                    " last_name = ?," +
                    " department_id = ?," +
                    " job_title = ?," +
                    " gender = ?," +
                    " date_of_birth = ?" +
                    " WHERE employee_id = ?;");
            ps.setString(1,employee.getFirstName());
            ps.setString(2,employee.getLastName());
            ps.setInt(3, employee.getDepartmentID());
            ps.setString(4, employee.getJobTitle());
            ps.setString(5, genderSQL(employee.getGender()));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            ps.setDate(6,new Date(employee.getDateOfBirth().getTime().getTime()));
            ps.setLong(7, employee.getEmployeeID());
            return ps;
        });
    }
    //removes a record by employee id.
    public Integer delete(Long employeeID){
        String query = "DELETE FROM " + table + " WHERE employee_id='"+employeeID+"';";
        Integer numberOfRowsDeleted = jdbcTemplate.update(query);
        //sequence manages autoincrement in the employee_id column
        //after successfully removing a record, put the sequence cursor on the deleted id
        jdbcTemplate.execute("ALTER SEQUENCE "+ sequence+" RESTART WITH "+employeeID+";");
        return numberOfRowsDeleted;
    }
    private String genderSQL(Gender gender){
        if (gender== Gender.MALE) return "m";
        else if (gender== Gender.FEMALE) return "f";
        else return "n/b";
    }
    //mapping for the "employee" table to retrieve Employee objects
    class EmployeeRowMapper implements RowMapper<Employee>{
        @Override
        public Employee mapRow (ResultSet rs, int rowNum) throws SQLException {
            Long employeeID = rs.getLong("employee_id");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            Integer departmentID = rs.getInt("department_id");
            String jobTitle = rs.getString("job_title");
            Gender gender;
            if (rs.getString("gender").equals("m")) gender = Gender.MALE;
            else if (rs.getString("gender").equals("f")) gender = Gender.FEMALE;
            else gender = Gender.OTHER;
            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
            Calendar dateOfBirth = Calendar.getInstance();
            try {
                dateOfBirth.setTime(simpleDate.parse(rs.getString("date_of_birth")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return  new Employee(employeeID, firstName, lastName,
                    departmentID, jobTitle, gender, dateOfBirth);
        }
    }
}
