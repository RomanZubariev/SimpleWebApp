package com.mastery.java.task.graphql;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import com.mastery.java.task.service.EmployeeService;
import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class GraphqlController {

  private final Logger log = LoggerFactory.getLogger(this.getClass());
  private final EmployeeService employeeService;

  @Autowired
  public GraphqlController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @QueryMapping
  public Employee employeeById(@Argument String id) {
    log.info("GraphQL: get employee, id = {}", id);
    return employeeService.getById(Long.valueOf(id));
  }

  @QueryMapping
  public List<Employee> allEmployees() {
    log.info("GraphQL, get all employees");
    return employeeService.getAll();
  }

  @MutationMapping
  public Employee updateEmployee(@Argument String id, @Argument String firstName, @Argument String lastName,
      @Argument Integer departmentId, @Argument String jobTitle, @Argument Gender gender,
      @Argument String dateOfBirth) {
    log.info("GraphQL API: update employee, id ={}", id);
    Employee employee = new Employee();
    employee.setEmployeeId(Long.valueOf(id));
    employee.setFirstName(firstName);
    employee.setLastName(lastName);
    employee.setDepartmentId(departmentId);
    employee.setJobTitle(jobTitle);
    employee.setGender(gender);
    employee.setDateOfBirth(LocalDate.parse(dateOfBirth));
    return employeeService.update(employee);
  }

  @MutationMapping
  public Employee createEmployee(@Argument String firstName, @Argument String lastName,
      @Argument Integer departmentId, @Argument String jobTitle, @Argument Gender gender,
      @Argument String dateOfBirth) {
    log.info("GraphQL API: create a new employee");
    Employee employee = new Employee();
    employee.setFirstName(firstName);
    employee.setLastName(lastName);
    employee.setDepartmentId(departmentId);
    employee.setJobTitle(jobTitle);
    employee.setGender(gender);
    employee.setDateOfBirth(LocalDate.parse(dateOfBirth));
    return employeeService.save(employee);
  }

  @MutationMapping
  public String deleteEmployee(@Argument String id) {
    log.info("GraphQL: delete employee, id = {}", id);
    employeeService.deleteById(Long.valueOf(id));
    return id;
  }
}
