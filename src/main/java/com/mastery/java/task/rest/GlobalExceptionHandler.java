package com.mastery.java.task.rest;

import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @ExceptionHandler(EmptyResultDataAccessException.class)
  public ResponseEntity<String> handleEmptyResultDataAccessException(
      EmptyResultDataAccessException exception) {
    if (Arrays.stream(exception.getStackTrace())
        .anyMatch(te -> te.getMethodName().matches("deleteById"))) {
      log.warn("DELETE method was used on non existing record.");
      return ResponseEntity.status(HttpStatus.OK).body("");
    }
    log.error(exception.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException exception) {
    log.error(exception.getMessage());
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exception.getMessage());
  }
}
