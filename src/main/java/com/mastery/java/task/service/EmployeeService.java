package com.mastery.java.task.service;
import com.mastery.java.task.dao.EmployeeDao;
import com.mastery.java.task.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeService {
    @Autowired
    private EmployeeDao employeeDao;
    public Employee getEmployeeByID(Long id){
        return employeeDao.retrieve(id);
    }
    public List<Employee> getEmployeeByName(String firstName, String lastName){
        return employeeDao.retrieveAll().stream()
                .filter(employee -> (employee.getFirstName().equals(firstName)&&employee.getLastName().equals(lastName)))
                .collect(Collectors.toList());
    }
    public List<Employee> getAllEmployees(){
        return employeeDao.retrieveAll();
    }
    public Long createEmployeeRecord(Employee employee){
        Long newID=-1L;
        while(newID==-1L){
            try{
                newID = employeeDao.create(employee);
            } catch (DuplicateKeyException e){
            }
        }
        return newID;
    }
    public Integer updateEmployeeRecord(Employee employee){
        return employeeDao.update(employee);
    }
    public Integer deleteEmployeeRecord(Long id){
        return employeeDao.delete(id);
    }

}
