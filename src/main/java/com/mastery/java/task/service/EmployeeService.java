package com.mastery.java.task.service;

import com.mastery.java.task.dao.Dao;
import com.mastery.java.task.dto.Employee;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeService implements Service<Employee> {

  @Autowired
  private Dao<Employee> employeeDao;

  @Override
  public Employee getById(Long id) {
    return employeeDao.getById(id);
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
