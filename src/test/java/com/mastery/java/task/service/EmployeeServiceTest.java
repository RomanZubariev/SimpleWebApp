package com.mastery.java.task.service;

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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

  @Mock
  private EmployeeRepository employeeRepository;

  @InjectMocks
  private EmployeeServiceImpl employeeServiceImpl;
  private Employee testEmployee;

  @BeforeEach
  void setup() {
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
  void getEmployeeTest() {
    Long id = 1L;
    when(employeeRepository.findById(id)).thenReturn(Optional.ofNullable(testEmployee));
    Assertions.assertEquals(testEmployee, employeeServiceImpl.getById(id));
    Mockito.verify(employeeRepository, times(1)).findById(any());
  }

  @Test
  void getNullEmployeeTest() {
    Long id = 1L;
    when(employeeRepository.findById(id)).thenReturn(Optional.empty());
    EmptyResultDataAccessException e = Assertions.assertThrows(EmptyResultDataAccessException.class,
        () -> employeeServiceImpl.getById(id));
    Assertions.assertTrue(e.getMessage().contains("Cannot get employee"));
    Assertions.assertTrue(e.getMessage().contains(id.toString()));
  }

  @Test
  void saveEmployeeWithIdSetUp() {
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> employeeServiceImpl.save(testEmployee));
  }

  @Test
  void saveEmployeeWithoutId() {
    testEmployee.setEmployeeId(null);
    when(employeeRepository.save(testEmployee)).thenReturn(testEmployee);
    Assertions.assertEquals(testEmployee, employeeServiceImpl.save(testEmployee));
  }

  @Test
  void deleteCheckCallsNumber() {
    Long id = 1L;
    employeeServiceImpl.deleteById(id);
    verify(employeeRepository, times(1)).deleteById(any());
  }

  @Test
  void updateNonExistingEmployee() {
    when(employeeRepository.findById(testEmployee.getEmployeeId())).thenReturn(Optional.empty());
    EmptyResultDataAccessException e = Assertions.assertThrows(EmptyResultDataAccessException.class,
        () -> employeeServiceImpl.update(testEmployee));
    Assertions.assertTrue(e.getMessage().contains("Cannot update employee"));
    Assertions.assertTrue(e.getMessage().contains(testEmployee.getEmployeeId().toString()));
  }

  @Test
  void updateExistingEmployee() {
    when(employeeRepository.findById(testEmployee.getEmployeeId())).thenReturn(
        Optional.of(testEmployee));
    when(employeeRepository.save(testEmployee)).thenReturn(testEmployee);
    Assertions.assertTrue(employeeServiceImpl.update(testEmployee).equals(testEmployee));
  }
}
