package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmployeeDaoTest {

  @Autowired
  EmployeeDaoImpl employeeDaoImpl;

  //Adds Test Surname employee record and prints it id in the table
  @Test
  public void testAddRecord() {
    String firstName = "Test";
    String lastName = "Surname";
    Integer departmentID = 1;
    String jobTitle = "Title";
    Gender gender = Gender.FEMALE;
    LocalDate date = LocalDate.now();
    Employee employee = new Employee(20L, firstName, lastName, departmentID, jobTitle, gender,
        date);
    employeeDaoImpl.save(employee);
  }

  /*removes all records with the name "Test" starting with the largest id*/
  @Test
  public void testDeleteRecord() {
    List<Employee> employeeList = employeeDaoImpl.getAll();
    employeeList.stream().filter(emp -> emp.getFirstName().equals("Test"))
        .sorted(Comparator.comparingLong(Employee::getEmployeeId).reversed())
        .forEach(employee -> employeeDaoImpl.deleteById(employee.getEmployeeId()));

  }
}
