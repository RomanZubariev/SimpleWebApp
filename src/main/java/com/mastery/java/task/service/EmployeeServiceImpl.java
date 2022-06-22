package com.mastery.java.task.service;

import com.mastery.java.task.dto.Employee;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

  private final CrudRepository<Employee, Long> employeeRepository;
  private EmptyResultDataAccessException notFoundException;

  @Autowired
  public EmployeeServiceImpl(CrudRepository<Employee, Long> employeeRepository) {
    this.employeeRepository = employeeRepository;
    notFoundException = new EmptyResultDataAccessException("Employee not found", 1);
  }

  @Override
  public Employee getById(Long id) {
    return employeeRepository.findById(id).orElseThrow(() -> notFoundException);
  }

  @Override
  public List<Employee> getAll() {
    List<Employee> employeeList = new ArrayList<>();
    employeeRepository.findAll().forEach(employeeList::add);
    return employeeList;
  }

  @Override
  public Employee save(Employee employee) {
    employee.setEmployeeId(null);
    return employeeRepository.save(employee);
  }

  @Override
  public Employee update(Employee employee) {
    if (employeeRepository.existsById(employee.getEmployeeId())) {
      return employeeRepository.save(employee);
    } else {
      throw notFoundException;
    }
  }

  @Override
  public void deleteById(Long id) {
    employeeRepository.deleteById(id);
  }

}
