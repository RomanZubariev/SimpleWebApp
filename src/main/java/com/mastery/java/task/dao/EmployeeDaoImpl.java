package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

  private EmployeeRepository employeeRepository;

  @Autowired
  public EmployeeDaoImpl(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @Override
  public Employee getById(Long id) {
    return employeeRepository.findById(id).get();
  }

  //read the whole table as a List of Employee
  @Override
  public List<Employee> getAll() {
    return employeeRepository.findAll();
  }

  //Adds a new employee to the db and returns its employeeID generated automatically by the db
  @Override
  public Employee save(Employee employee) {
    employee.setEmployeeId(null);
    return employeeRepository.save(employee);
  }

  // updates the record with an employee_id of the employee
  @Override
  public Employee update(Employee employee) {
    if (employeeRepository.findById(employee.getEmployeeId()).isPresent()) {
      return employeeRepository.save(employee);
    } else {
      return null;
    }
  }

  //removes a record by employee id.
  @Override
  public void deleteById(Long employeeId) {
    employeeRepository.deleteById(employeeId);
  }
}
