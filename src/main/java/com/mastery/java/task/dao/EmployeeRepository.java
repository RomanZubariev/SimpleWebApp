package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

}
