package com.mastery.java.task.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmployeeBirthDateValidator implements ConstraintValidator<EmployeeBirthDate, LocalDate> {

  @Override
  public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
    return !date.isAfter(LocalDate.now().minusYears(18));
  }

}
