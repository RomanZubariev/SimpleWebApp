package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDao {

  Employee getById(Long id);

  List<Employee> getAll();

  Employee save(Employee t);

  Employee update(Employee t);

  void deleteById(Long id);
}