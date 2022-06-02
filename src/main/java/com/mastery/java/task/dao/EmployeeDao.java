package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class EmployeeDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String table="employee";

    public Employee retrieve(Long id){
        String query = "Select*From "+table+ " WHERE employee_id='"+id+"';";
        Employee employee = jdbcTemplate.queryForObject(query,new EmployeeRowMapper());
        return employee;
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
        String query = sqlInsertMapper(employee);
        query = "INSERT INTO "+ table+ " ( "+
            "first_name, last_name, department_id, job_title, gender, date_of_birth"+
            ") VALUES (" +query+");";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(query);
        pscf.setReturnGeneratedKeys(true);
        jdbcTemplate.update(pscf.newPreparedStatementCreator(new ArrayList<>()), keyHolder);
        return ((Number) keyHolder.getKeys().get("employee_id")).longValue();
    }
    // updates the record with an employee_id of the employee
    public Integer update (Employee employee){
        String gender = (employee.getGender()==Gender.MALE)?"m":((employee.getGender()==Gender.FEMALE)?"f":"n/b");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateOfBirth = dateFormat.format(employee.getDateOfBirth().getTime());
        String query = "UPDATE employee " +
                "SET  first_name = '" + employee.getFirstName()+
                "', last_name = '" + employee.getLastName()+
                "', department_id = " + employee.getDepartmentID()+
                ", job_title = '" + employee.getJobTitle() +
                "', gender = '" + gender +
                "', date_of_birth = '" + dateOfBirth +
                "' WHERE employee_id = " + employee.getEmployeeID()+ ";";
        return jdbcTemplate.update(query);
    }
    //removes a record by employee id.
    public Integer delete(Long employeeID){
        String query = "DELETE FROM " + table + " WHERE employee_id='"+employeeID+"';";
        Integer numberOfRowsDeleted = jdbcTemplate.update(query);
        jdbcTemplate.execute("ALTER SEQUENCE my_serial RESTART WITH "+employeeID+";");
        return numberOfRowsDeleted;
    }
    // map the VALUES from Employee for INSERT statements
    private String sqlInsertMapper (Employee employee){
        //Adding VALUES to sql query one by one separated by ","
        String query = "'"+employee.getFirstName()+"'";
        query += ","+"'"+employee.getLastName()+"'";
        query += ","+ employee.getDepartmentID();
        query += "," + "'"+employee.getJobTitle()+"'";
        if (employee.getGender()== Gender.MALE) query += "," + "'m'";
        else if (employee.getGender()== Gender.FEMALE) query += "," + "'f'";
        else query += "," + "'n/b'";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        query += "," + "'"+dateFormat.format(employee.getDateOfBirth().getTime())+"'";
        return query;
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
            String genderString = rs.getString("gender");
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
