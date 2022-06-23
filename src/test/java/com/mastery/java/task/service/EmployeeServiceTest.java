package com.mastery.java.task.service;

import static org.mockito.Mockito.when;

import com.mastery.java.task.dao.EmployeeRepository;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

  @Mock
  private EmployeeRepository employeeRepository;

  @InjectMocks
  private EmployeeServiceImpl employeeServiceImpl;

  private Employee testEmployee;

  @BeforeEach
  public void setup() {
    employeeServiceImpl = new EmployeeServiceImpl(employeeRepository);
    testEmployee = new Employee();
    testEmployee.setEmployeeId(1L);
    testEmployee.setFirstName("Test");
    testEmployee.setLastName("Surname");
    testEmployee.setDepartmentId(1);
    testEmployee.setJobTitle("Job title");
    testEmployee.setGender(Gender.MALE);
    testEmployee.setDateOfBirth(LocalDate.now());
  }

  @Test
  public void getEmployeeTest() {
    when(employeeRepository.findById(80L)).thenReturn(Optional.ofNullable(testEmployee));
    Assertions.assertEquals(testEmployee, employeeServiceImpl.getById(80L));
  }

  @Test
  public void getNullEmployeeTest() {
    when(employeeRepository.findById(100L)).thenReturn(Optional.ofNullable(null));
    Assertions.assertThrows(EmptyResultDataAccessException.class,
        () -> employeeServiceImpl.getById(100L));
  }

  @Test
  public void saveEmployeeWithIdSetUp() {
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> employeeServiceImpl.save(testEmployee));
  }
}
