package com.mastery.java.task.dto;

import java.util.Calendar;

public class Employee {
    private Long employeeID;
    private String firstName;
    private String lastName;
    private Integer departmentID;
    private String jobTitle;
    private Gender gender;
    private Calendar dateOfBirth;
    public Employee(Long employeeID, String firstName, String lastName, Integer departmentID,
                    String jobTitle, Gender gender, Calendar dateOfBirth){
        this.employeeID = employeeID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.departmentID = departmentID;
        this.jobTitle = jobTitle;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public Long getEmployeeID(){
        return this.employeeID;
    }
    public void setEmployeeID(Long employeeID){
        this.employeeID = employeeID;
    }
    public Integer getDepartmentID(){
        return this.departmentID;
    }
    public void setDepartmentID(Integer departmentID){
        this.departmentID = departmentID;
    }
    public String getJobTitle(){
        return jobTitle;
    }
    public void setJobTitle(String jobTitle){
        this.jobTitle=jobTitle;
    }
    public String getFirstName(){
        return this.firstName;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public String getLastName(){
        return this.lastName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public Gender getGender(){
        return this.gender;
    }
    public void setGender(Gender gender){
        this.gender = gender;
    }
    public Calendar getDateOfBirth(){
        return (Calendar) dateOfBirth.clone();
    }
    public void setDateOfBirth(Calendar dateOfBirth){
        this.dateOfBirth = (Calendar) dateOfBirth.clone();
    }
    @Override
    public Employee clone(){
        return new Employee(this.employeeID, this.firstName, this.lastName, this.departmentID,
                this.getJobTitle(), this.gender, this.dateOfBirth);
    }
    @Override
    public boolean equals(Object o){
        if (o==this) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        if (this.employeeID ==employee.employeeID) return true;
        return false;
    }
    @Override
    public int hashCode(){
        int result = 7;
        int prime = 31;
        result = prime*result + (int) (this.employeeID ^ (this.employeeID >>> 32));
        return result;
    }
}
