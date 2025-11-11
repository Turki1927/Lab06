package org.example.employeemangementsystemlab6.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Employee {

    @NotNull(message = "id can not be empty")
    @Size(min = 3 , message = "The length of id must be greater than 2")
    private  String ID;

    @NotEmpty(message = "enter the name please")
    @Size(min = 5 , message = "The length of name must be greater than 4")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Please enter only characters")
    private  String name;

    @NotEmpty(message = "please enter your email")
    @Email
    private String email;

    @NotEmpty(message = "Enter the phone number")
    @Pattern(regexp = "^05\\d{8}$", message = "The phone number must start with 05 and contain 10 digit")
    private String phoneNumber;

    @NotNull(message = "please enter your age")
    @Positive(message = "Enter only positive number")
    @Min(value = 26 , message = "The age must be greater than 25")
    private int age;

    @NotEmpty(message = "please Enter your position")
    @Pattern(regexp = "supervisor|coordinator", message = "The value must be either supervisor or coordinator ")
    private String  position;

    @NotNull(message = "you must enter the value of on leave or not")
    @AssertFalse
    private boolean onLeave=false;

    @NotNull
    @PastOrPresent(message = "The date of hire must be in past or present ")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate hireDate;

    @NotNull(message = "please enter Annual Leave")
    @Positive(message = "The annual Leave must be positive number only")
    @Min(value = 0)
    private int AnnualLeave;



}
