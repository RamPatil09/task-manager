package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDto {

    @NotBlank(message = "Organization can't be empty!")
    private String companyName;
    @NotBlank(message = "Register number can't be empty!")
    private String reg_number;
    @Email
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Please provide a valid email address")
    private String business_email;
    @NotBlank(message = "Please provide a valid phone number")
    private String phone_number;
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least {min} characters long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).*$",
            message = "Password must contain at least one lowercase letter, "
                    + "one uppercase letter, "
                    + "one digit, "
                    + "and one special character")
    private String password;
    private boolean approved;
}
