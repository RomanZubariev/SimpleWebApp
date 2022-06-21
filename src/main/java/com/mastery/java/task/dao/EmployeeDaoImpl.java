package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

  private final EmployeeRepository employeeRepository;

  @Autowired
  public EmployeeDaoImpl(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @Override
  public Employee getById(Long id) {
    return employeeRepository.findById(id).orElseThrow();
  }

  @Override
  public List<Employee> getAll() {
    return employeeRepository.findAll();
  }

  @Override
  public Employee save(Employee employee) {
    employee.setEmployeeId(null);
    return employeeRepository.save(employee);
  }

  @Override
  public Employee update(Employee employee) {
    if (employeeRepository.findById(employee.getEmployeeId()).isPresent()) {
      return employeeRepository.save(employee);
    } else {
      return null;
    }
  }

  @Override
  public void deleteById(Long employeeId) {
    employeeRepository.deleteById(employeeId);
  }
}
