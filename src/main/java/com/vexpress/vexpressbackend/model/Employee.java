package com.vexpress.vexpressbackend.model;

import jakarta.persistence.*;

//validation//
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "First Name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last Name cannot be blank")
    private String lastName;

    @NotBlank(message = "City cannot be blank")
    private String city;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Please enter a valid email")
    private String email;

    @NotBlank(message = "Mobile Number cannot be blank")
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Mobile Number must contain exactly 10 digits"
    )
    private String mobileNumber;

    @NotBlank(message = "Department cannot be blank")
    private String department;

    @NotBlank(message = "Designation cannot be blank")
    private String designation;

    public Employee() {
    }

    public Employee(Long id, String firstName, String lastName, String city, String mobileNumber, String email, String department, String designation) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.department = department;
        this.designation = designation;
    }

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

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

    public String getDepartment() {return department;}

    public void setDepartment(String department) {this.department = department;}

    public String getDesignation() {return designation;}

    public void setDesignation(String designation) {this.designation = designation;}
}
