package com.mastery.java.task.rest;

import java.util.NoSuchElementException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(EmptyResultDataAccessException.class)
  public final ResponseEntity handleEmptyResultDataAccessException(
      EmptyResultDataAccessException exception) {
    return new ResponseEntity(HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(NoSuchElementException.class)
  public final ResponseEntity handleNoSuchElementException(NoSuchElementException exception) {
    return new ResponseEntity(HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ResponseStatusException.class)
  public final ResponseEntity handleResponseStatusException(ResponseStatusException e) {
    return new ResponseEntity(e.getStatus());
  }

}
