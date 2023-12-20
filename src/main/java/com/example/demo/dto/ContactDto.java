package com.example.demo.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {

    @NotBlank(message = "First name can't be empty!!")
    @Size(min = 5, max = 15, message = "The first name must be between {min} and {max} characters long.")
    private String firstname;

    @NotBlank(message = "Last name can't be empty!!")
    private String lastname;

    @Email
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Please provide a valid email address")
    private String workemail;

    private String phonenumber;

    @NotBlank(message = "Company name can't be empty!!")
    private String companyname;

    @NotNull(message = "Please provide size of company can't be empty!!")
    private Integer companysize;

    @NotBlank(message = "Role can't be empty!!")
    private String role;

    @NotBlank(message = "Job level can't be empty!!")
    private String joblevel;

    @NotBlank(message = "Please complete this required field!!")
    private String discuss;
}
