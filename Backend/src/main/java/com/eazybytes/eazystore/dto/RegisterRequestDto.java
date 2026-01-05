package com.eazybytes.eazystore.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterRequestDto {

    @NotBlank(message = "Name is required")
    @Size(min=3, max=30, message="The length of name should be between 5 and 100 character")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message="Email address must be a valid value")
    private String email;

    @NotBlank(message = "Mobile Number is required")
    @Pattern(regexp="^\\d{10}$", message="Mobile number must be exactly 10 digits")
    private String mobileNumber;

    @NotBlank(message = "Password is required")
    @Size(min=8, max=20, message="The length of password should be between 5 and 100 character")
    private String password;
}
