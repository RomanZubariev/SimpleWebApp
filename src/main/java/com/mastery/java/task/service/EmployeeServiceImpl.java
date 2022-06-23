package com.mastery.java.task.service;

import com.mastery.java.task.dao.EmployeeRepository;
import com.mastery.java.task.dto.Employee;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
        () -> new EmptyResultDataAccessException("Cannot get employee: id " + id + " not found",
            1));
  }

  @Override
  public List<Employee> getAll() {
    return employeeRepository.findAll();
  }

  @Override
  public Employee save(Employee employee) {
    Optional.ofNullable(employee.getEmployeeId()).ifPresent((id) -> {
      throw new IllegalArgumentException("Employee id should be null.");
    });
    return employeeRepository.save(employee);
  }

  @Override
  public Employee update(Employee employee) {
    employeeRepository.findById(employee.getEmployeeId()).orElseThrow(
        () -> new EmptyResultDataAccessException(
            "Cannot update employee: id " + employee.getEmployeeId() + " not found", 1));
    return employeeRepository.save(employee);
  }

  @Override
  public void deleteById(Long id) {
    employeeRepository.deleteById(id);
  }

}
