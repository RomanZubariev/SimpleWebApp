package com.mastery.java.task.rest;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.service.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeController implements Controller<Employee> {

  @Autowired
  Service<Employee> employeeService;

  @Override
  public Employee get(Long id) {
    Optional<Employee> optEmployee = employeeService.getById(id);
    return optEmployee.get();
  }

  @Override
  public List<Employee> getAll() {
    return employeeService.getAll();
  }

  @Override
  public void save(Employee employee) {
    employeeService.save(employee);
  }

  @Override
  public void update(Employee employee, Long id) {
    employee.setEmployeeId(id);
    employeeService.update(employee);
  }

  @Override
  public void delete(Long id) {
    employeeService.deleteById(id);
  }

}
