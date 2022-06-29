package com.mastery.java.task.service;

import com.mastery.java.task.dao.EmployeeRepository;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import java.time.LocalDate;
import java.util.List;
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
  private Employee testEmployee2;

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

    testEmployee2 = new Employee();
    testEmployee2.setEmployeeId(2L);
    testEmployee2.setFirstName("Test");
    testEmployee2.setLastName("Surname");
    testEmployee2.setDepartmentId(2);
    testEmployee2.setJobTitle("Job title");
    testEmployee2.setGender(Gender.FEMALE);
    testEmployee2.setDateOfBirth(LocalDate.now());
  }

  @Test
  void getEmployeeTest() {
    Long id = 1L;
    when(employeeRepository.findById(id)).thenReturn(Optional.ofNullable(testEmployee));
    Assertions.assertEquals(testEmployee, employeeServiceImpl.getById(id));
    Mockito.verify(employeeRepository, times(1)).findById(id);
  }

  @Test
  void getNullEmployeeTest() {
    Long id = 1L;
    when(employeeRepository.findById(id)).thenReturn(Optional.empty());
    EmptyResultDataAccessException e = Assertions.assertThrows(EmptyResultDataAccessException.class,
        () -> employeeServiceImpl.getById(id));
    Assertions.assertEquals("Cannot find employee with id = " + id, e.getMessage());
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
    verify(employeeRepository, times(1)).deleteById(id);
  }

  @Test
  void updateNonExistingEmployee() {
    when(employeeRepository.existsById(testEmployee.getEmployeeId())).thenReturn(false);
    EmptyResultDataAccessException e = Assertions.assertThrows(EmptyResultDataAccessException.class,
        () -> employeeServiceImpl.update(testEmployee));
    Assertions.assertEquals("Cannot update employee info because such employee doesn't exist: id = "
        + testEmployee.getEmployeeId(), e.getMessage());
  }

  @Test
  void updateExistingEmployee() {
    when(employeeRepository.existsById(testEmployee.getEmployeeId())).thenReturn(true);
    when(employeeRepository.save(testEmployee)).thenReturn(testEmployee);
    Assertions.assertEquals(employeeServiceImpl.update(testEmployee), testEmployee);
  }

  @Test
  void getAllCheckElements() {
    when(employeeRepository.findAll()).thenReturn(List.of(testEmployee, testEmployee2));
    Assertions.assertTrue(employeeServiceImpl.getAll().contains(testEmployee));
    Assertions.assertTrue(employeeServiceImpl.getAll().contains(testEmployee2));
    Assertions.assertEquals(2, employeeServiceImpl.getAll().size());
  }
}
