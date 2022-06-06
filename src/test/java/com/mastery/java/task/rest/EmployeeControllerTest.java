package com.mastery.java.task.rest;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Comparator;

@RunWith(SpringRunner.class )
@SpringBootTest
public class EmployeeControllerTest {
    @Autowired
    EmployeeController employeeController;
    @Test
    public void put (){
        String firstName = "Test";
        String lastName = "Surname";
        Integer departmentID = 1;
        String jobTitle = "Title";
        Gender gender = Gender.FEMALE;
        Calendar date = Calendar.getInstance();
        Employee employee = new Employee(1L, firstName, lastName, departmentID, jobTitle, gender, date );
        System.out.println(employeeController.create(employee));
    }
    @Test
    public void post (){
        Long employeeID = 20L;
        String firstName = "Test";
        String lastName = "Surname2";
        Integer departmentID = 2;
        String jobTitle = "Title";
        Gender gender = Gender.MALE;
        Calendar date = Calendar.getInstance();
        Employee employee = new Employee(employeeID, firstName, lastName, departmentID, jobTitle, gender, date );
        System.out.println(employeeController.updateRecord(employee));
    }
    @Test
    public void get (){
        employeeController.getEmployee(-1L).getBody()
                .forEach(employee -> System.out.println(employee.getEmployeeID()+
                        " "+employee.getFirstName()+" "+ employee.getLastName()));

    }
    /*removes all records with the name "Test" starting with the largest id (to set the auto
generated id sequence on the lowest id gap*/
    @Test
    public void delete (){
        employeeController.getEmployee(-1L).getBody()
                .stream().filter(emp->emp.getFirstName().equals("Test"))
                .sorted(Comparator.comparingLong(Employee::getEmployeeID).reversed())
                .forEach(employee -> employeeController.deleteRecord(employee.getEmployeeID()));
    }
}
