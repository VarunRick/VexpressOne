package com.vexpress.vexpressbackend.dto;

public class EmployeeResponseDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String city;
    private String email;
    private String mobileNumber;
    private String department;
    private String designation;

    public EmployeeResponseDTO() {
    }

    public EmployeeResponseDTO(Long id,
                               String firstName,
                               String lastName,
                               String city,
                               String email,
                               String mobileNumber,
                               String department,
                               String designation) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.department = department;
        this.designation = designation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}