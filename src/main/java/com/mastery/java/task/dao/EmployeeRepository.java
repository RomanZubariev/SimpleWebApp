package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

  @Override
  public List<Employee> findAll();

}
