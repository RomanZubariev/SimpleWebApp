package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class )
@SpringBootTest
public class EmployeeDaoTest {
    @Autowired
    EmployeeDao employeeDao;
    //Adds Test Surname employee record and prints it id in the table
    @Test
    public void testAddRecord (){
        String firstName = "Test";
        String lastName = "Surname";
        Integer departmentID = 1;
        String jobTitle = "Title";
        Gender gender = Gender.FEMALE;
        Calendar date = Calendar.getInstance();
        Employee employee = new Employee(1L, firstName, lastName, departmentID, jobTitle, gender, date );
        employee.setEmployeeID(employeeDao.create(employee));
        System.out.println("New employee_id = "+employee.getEmployeeID());
    }
    /*removes all records with the name "Test" starting with the largest id (to set the auto
    generated id sequence on the lowest id gap*/
    @Test
    public void testDeleteRecord(){
        List<Employee> employeeList = employeeDao.retrieveAll();
        employeeList.stream().filter(emp->emp.getFirstName().equals("Test"))
                .sorted(Comparator.comparingLong(Employee::getEmployeeID).reversed())
                .forEach(employee -> employeeDao.delete(employee.getEmployeeID()));

    }
}
