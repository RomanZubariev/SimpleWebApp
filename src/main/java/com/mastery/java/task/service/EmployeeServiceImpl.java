package com.mastery.java.task.service;

import com.mastery.java.task.dao.EmployeeRepository;
import com.mastery.java.task.dto.Employee;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeRepository employeeRepository;

  @Autowired
  public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @Override
  public Employee getById(Long id) {
    return employeeRepository.findById(id).orElseThrow(
        () -> new EmptyResultDataAccessException("Cannot find employee with id = " + id, 1));
  }

  @Override
  public List<Employee> getAll() {
    return Streamable.of(employeeRepository.findAll()).toList();
  }

  @Override
  public Employee save(Employee employee) {
    if (employee.getEmployeeId() != null) {
      throw new IllegalArgumentException("Employee id should be null.");
    }
    return employeeRepository.save(employee);
  }

  @Override
  public Employee update(Employee employee) {
    if (!employeeRepository.existsById(employee.getEmployeeId())) {
      throw new EmptyResultDataAccessException(
          "Cannot update employee info because such employee doesn't exist: id = "
              + employee.getEmployeeId(), 1);
    }
    return employeeRepository.save(employee);
  }

  @Override
  public void deleteById(Long id) {
    employeeRepository.deleteById(id);
  }
}
