package com.mastery.java.task.dto;

import java.time.LocalDate;
import javax.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmployeeDtoTest {

  @Mock
  private ConstraintValidatorContext context;
  @InjectMocks
  private EmployeeBirthDateValidator validator;

  @Test
  void shouldNotBeValidIfEighteenTomorrow() {
    Assertions.assertFalse(validator.isValid(LocalDate.now().minusYears(18).plusDays(1), context));
  }

  @Test
  void shouldBeValidIfTodayIsTheBirthDay() {
    Assertions.assertTrue(validator.isValid(LocalDate.now().minusYears(18), context));
  }

  @Test
  void shouldBeValidIfYesterdayIsTheBirthDay() {
    Assertions.assertTrue(validator.isValid(LocalDate.now().minusYears(18).minusDays(1), context));
  }
}
