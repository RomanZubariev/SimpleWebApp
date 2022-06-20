package com.mastery.java.task.dto;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long employeeId;

  private String firstName;
  private String lastName;
  private Integer departmentId;
  private String jobTitle;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  private LocalDate dateOfBirth;

  public Employee(Long employeeId, String firstName, String lastName, Integer departmentId,
      String jobTitle, Gender gender, LocalDate dateOfBirth) {
    this.employeeId = employeeId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.departmentId = departmentId;
    this.jobTitle = jobTitle;
    this.gender = gender;
    this.dateOfBirth = dateOfBirth;
  }

  public Employee() {
  }

  public Long getEmployeeId() {
    return this.employeeId;
  }

  public void setEmployeeId(Long employeeId) {
    this.employeeId = employeeId;
  }

  public Integer getDepartmentId() {
    return this.departmentId;
  }

  public void setDepartmentId(Integer departmentId) {
    this.departmentId = departmentId;
  }

  public String getJobTitle() {
    return jobTitle;
  }

  public void setJobTitle(String jobTitle) {
    this.jobTitle = jobTitle;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Gender getGender() {
    return this.gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public LocalDate getDateOfBirth() {
    return LocalDate.of(dateOfBirth.getYear(), dateOfBirth.getMonth(), dateOfBirth.getDayOfMonth());
  }

  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = LocalDate.of(dateOfBirth.getYear(), dateOfBirth.getMonth(),
        dateOfBirth.getDayOfMonth());
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Employee)) {
      return false;
    }
    Employee employee = (Employee) o;
    return this.employeeId.equals(employee.employeeId);
  }

  @Override
  public int hashCode() {
    int result = 7;
    int prime = 31;
    result = prime * result + (int) (this.employeeId ^ (this.employeeId >>> 32));
    return result;
  }
}
