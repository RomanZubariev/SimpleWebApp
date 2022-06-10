package com.mastery.java.task.service;

import com.mastery.java.task.dao.Dao;
import com.mastery.java.task.dto.Employee;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

@Component
public class EmployeeService implements Service<Employee> {

  @Autowired
  private Dao<Employee> employeeDao;

  @Override
  public Optional<Employee> getById(Long id) {
    try {
      return employeeDao.getById(id);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  @Override
  public List<Employee> getAll() {
    return employeeDao.getAll();
  }

  //Adds a record with id generated automatically
  @Override
  public void save(Employee employee) {
    employeeDao.save(employee);
  }

  @Override
  public void update(Employee employee) {
    employeeDao.update(employee);
  }

  @Override
  public void deleteById(Long id) {
    employeeDao.deleteById(id);
  }

}
