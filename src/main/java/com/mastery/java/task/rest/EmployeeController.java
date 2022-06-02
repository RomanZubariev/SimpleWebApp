package com.mastery.java.task.rest;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;
    @GetMapping("/employee")
    public ResponseEntity<List<Employee>> getEmployee(@RequestParam(value = "id", defaultValue = "-1")Long employeeID){
        List<Employee> employeeList = new ArrayList<>();
        if (employeeID.equals(-1L)) employeeList = employeeService.getAllEmployees();
        else  employeeList.add(employeeService.getEmployeeByID(employeeID));
        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }
    @PutMapping(path = "/employee",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> create(@RequestBody Employee employee) {
        Long newID = employeeService.createEmployeeRecord(employee);
        return new ResponseEntity<>(newID, HttpStatus.CREATED);
    }
    @PostMapping(path = "/employee",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateRecord(@RequestBody Employee employee) {
        if (employeeService.updateEmployeeRecord(employee)==1)
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        else return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/employee")
    public ResponseEntity deleteRecord(@RequestParam (value = "id", defaultValue = "-1") Long employeeID){
        if (employeeID==-1L) return new ResponseEntity(HttpStatus.NOT_FOUND);
        if (employeeService.deleteEmployeeRecord(employeeID)==1) return new ResponseEntity(HttpStatus.NO_CONTENT);
        else  return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

}
