package com.mastery.java.task.rest;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.service.EmployeeService;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class EmployeeJms {

  private final Logger log = LoggerFactory.getLogger(EmployeeJms.class);
  private final EmployeeService employeeService;

  @Autowired
  public EmployeeJms(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @JmsListener(destination = "EmployeeQueue", containerFactory = "containerFactory")
  @SendTo("ResponseQueue")
  public String save(@Valid Employee employee) {
    log.info("Message received to EmployeeQueue");
    Long id = employeeService.save(employee).getEmployeeId();
    String response = "New employee has been saved: id = " + id;
    log.info(response);
    return response;
  }

}
