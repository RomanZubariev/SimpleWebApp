package com.mastery.java.task.service;

import com.mastery.java.task.dto.Employee;
import java.util.List;

public interface EmployeeService {

  Employee getById(Long id);

  List<Employee> getAll();

  Employee save(Employee t);

  Employee update(Employee t);

  void deleteById(Long id);

}
