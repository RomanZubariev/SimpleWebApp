package com.mastery.java.task.rest;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.service.Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

  Service<Employee> employeeService;

  @Autowired
  public EmployeeController(Service<Employee> employeeService) {
    this.employeeService = employeeService;
  }

  @GetMapping("/{id}")
  public Employee get(@PathVariable Long id) {
    return employeeService.getById(id);
  }

  @GetMapping("/")
  public List<Employee> getAll() {
    return employeeService.getAll();
  }

  @PostMapping("/")
  public Employee save(@RequestBody Employee employee) {
    return employeeService.save(employee);
  }

  @PutMapping("/{id}")
  public Employee update(@RequestBody Employee employee, @PathVariable Long id) {
    employee.setEmployeeId(id);
    return employeeService.update(employee);
  }

  @DeleteMapping("/{id}")
  public Employee delete(@PathVariable Long id) {
    return employeeService.deleteById(id);
  }

}
