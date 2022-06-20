package com.mastery.java.task.service;

import com.mastery.java.task.dao.EmployeeDao;
import com.mastery.java.task.dto.Employee;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeDao employeeDao;

  @Autowired
  public EmployeeServiceImpl(EmployeeDao employeeDao) {
    this.employeeDao = employeeDao;
  }

  @Override
  public Employee getById(Long id) {
    try{
      return employeeDao.getById(id);
    } catch (NoSuchElementException e){
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
    }
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
    if (employeeDao.update(employee) != null) {
      return employee;
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
    }
  }

  @Override
  public void deleteById(Long id) {
    try {
      employeeDao.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
    }

  }

}
