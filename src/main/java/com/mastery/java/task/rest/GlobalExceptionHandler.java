package com.mastery.java.task.rest;

import java.util.Arrays;
import java.util.stream.Collectors;
import javax.jms.JMSException;
import javax.validation.ConstraintViolationException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.listener.adapter.ListenerExecutionFailedException;
import org.springframework.util.ErrorHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler implements ErrorHandler {

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

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<String> handleConstraintViolationException(
      ConstraintViolationException exception) {
    log.error(exception.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
  }

  @Override
  public ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status,
      WebRequest request) {
    String validationMessages = exception.getBindingResult().getFieldErrors().stream()
        .map(fe -> fe.getField() + ": " + fe.getDefaultMessage()).collect(Collectors.joining("\n"));
    log.error(validationMessages);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationMessages);
  }

  @ExceptionHandler(ListenerExecutionFailedException.class)
  public void handleListenerExecutionFailedException(ListenerExecutionFailedException exception) {
    log.error("Invalid message sent: {}", exception.getCause().getMessage());
  }

  @ExceptionHandler
  public void jmsExceptionHandler(JMSException e) {
    log.error(e.getMessage());
  }

  @Override
  public void handleError(Throwable e) {
    if (ExceptionUtils.getRootCause(e).getClass() == ConstraintViolationException.class) {
      String violations = ((ConstraintViolationException) ExceptionUtils.getRootCause(
          e)).getConstraintViolations().stream()
          .map(cv -> cv.getPropertyPath() + ": " + cv.getMessage())
          .collect(Collectors.joining("\n"));
      log.error(violations);
    } else {
      log.error(ExceptionUtils.getRootCause(e).getMessage());
    }
  }
}
