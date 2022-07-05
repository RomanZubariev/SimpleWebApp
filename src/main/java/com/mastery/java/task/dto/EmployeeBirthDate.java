package com.mastery.java.task.dto;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = EmployeeBirthDateValidator.class)
@Documented
public @interface EmployeeBirthDate {

  String message() default "must be at least 18 y.o.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}