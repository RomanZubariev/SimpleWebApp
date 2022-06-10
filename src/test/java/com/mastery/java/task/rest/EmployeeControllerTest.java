package com.mastery.java.task.rest;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeControllerTest {

  @Autowired
  EmployeeController employeeController;

  @Test
  public void post() {
    String firstName = "Test";
    String lastName = "Surname";
    Integer departmentID = 1;
    String jobTitle = "Title";
    Gender gender = Gender.FEMALE;
    LocalDate date = LocalDate.now();
    Employee employee = new Employee(1L, firstName, lastName, departmentID, jobTitle, gender, date);
    employeeController.save(employee);
    List<Employee> list = employeeController.getAll();
    employee.setEmployeeId(list.get(list.size()-1).getEmployeeId());
    System.out.println(employee.getEmployeeId()+" "+employee.getFirstName()+" "+employee.getLastName());
  }

  @Test
  public void put() {
    Long employeeId = 21L;
    String firstName = "Test";
    String lastName = "Surname";
    Integer departmentID = 2;
    String jobTitle = "Title";
    Gender gender = Gender.MALE;
    LocalDate date = LocalDate.now();
    Employee employee = new Employee(employeeId, firstName, lastName, departmentID, jobTitle,
        gender, date);
    employeeController.update(employee, employeeId);
  }

  @Test
  public void get() {
    employeeController.getAll()
        .forEach(employee -> System.out.println(
            employeeController.get(employee.getEmployeeId()).getLastName()));

  }

  /*removes all records with the name "Test" starting with the largest id*/
  @Test
  public void delete() {
    employeeController.getAll().stream()
        .filter(emp -> emp.getFirstName().equals("Test"))
        .sorted(Comparator.comparingLong(Employee::getEmployeeId).reversed())
        .forEach(employee -> employeeController.delete(employee.getEmployeeId()));
  }
}
