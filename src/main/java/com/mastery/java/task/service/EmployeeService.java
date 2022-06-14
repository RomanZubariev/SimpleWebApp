package com.mastery.java.task.service;

import com.mastery.java.task.dao.Dao;
import com.mastery.java.task.dto.Employee;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;


@org.springframework.stereotype.Service
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
  public Employee save(Employee employee) {
    return employeeDao.save(employee);
  }

  @Override
  public Employee update(Employee employee) {
    return employeeDao.update(employee);
  }

  @Override
  public Employee deleteById(Long id) {
    return employeeDao.deleteById(id);
  }

}
