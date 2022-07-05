package com.mastery.java.task.dto;

import java.time.LocalDate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmployeeBirthDateValidator implements ConstraintValidator<EmployeeBirthDate, LocalDate> {

  @Override
  public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
    return date.isBefore(LocalDate.now().minusYears(18));
  }

}
