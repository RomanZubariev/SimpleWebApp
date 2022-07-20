package com.mastery.java.task.jms;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import com.mastery.java.task.service.EmployeeService;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmployeeJmsTest {

  Employee correctEmployee;
  Employee incorrectEmployee;
  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  private EmployeeService employeeService;
  @InjectMocks
  private EmployeeJms employeeJms;

  @BeforeEach
  void setup() {
    correctEmployee = new Employee();
    correctEmployee.setEmployeeId(null);
    correctEmployee.setFirstName("Test");
    correctEmployee.setLastName("Surname");
    correctEmployee.setDepartmentId(1);
    correctEmployee.setJobTitle("Job title");
    correctEmployee.setGender(Gender.MALE);
    correctEmployee.setDateOfBirth(LocalDate.now().minusYears(18));
  }

  @Test
  void shouldHaveIdInResponse() {
    Long id = 1L;
    Mockito.when(employeeService.save(correctEmployee).getEmployeeId()).thenReturn(id);
    Assertions.assertTrue(employeeJms.save(correctEmployee).contains(id.toString()));
  }
}
