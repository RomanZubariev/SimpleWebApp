package com.mastery.java.task.rest;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.service.EmployeeService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
@Validated
public class EmployeeController {

  private final Logger log = LoggerFactory.getLogger(this.getClass());
  private final EmployeeService employeeService;

  @Autowired
  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @GetMapping("/{id}")
  public Employee get(@PathVariable @Min(1) @Max(Long.MAX_VALUE) Long id) {
    log.info("GET request received, id = {}", id);
    return employeeService.getById(id);
  }

  @GetMapping
  public List<Employee> getAll() {
    log.info("GET request received");
    return employeeService.getAll();
  }

  @PostMapping
  public Employee save(@Valid @RequestBody Employee employee) {
    log.info("POST request received");
    return employeeService.save(employee);
  }

  @PutMapping
  public Employee update(@Valid @RequestBody Employee employee) {
    log.info("PUT request received, id = {}", employee.getEmployeeId());
    return employeeService.update(employee);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable @Min(1) @Max(Long.MAX_VALUE) Long id) {
    log.info("DELETE request received, id = {}", id);
    employeeService.deleteById(id);
  }

}
