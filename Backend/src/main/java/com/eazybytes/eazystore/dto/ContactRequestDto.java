package com.eazybytes.eazystore.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactRequestDto {

    @NotBlank(message = "name cannot be empty")
    @Size(min =5, max=30, message = "name must be between 5 and 30 characters")
    private String name;

    @NotBlank(message="email cannot be empty")
    @Email(message="Invalid email address")
    private String email;

    @NotBlank(message="mobile number cannot be empty")
    @Pattern(regexp = "^\\d{10}$", message="Mobile number must be 10 digits")
    private String mobileNumber;

//    @NotBlank(message="message cannot be empty")
//    @Size(min = 5, max=500, message = "MEssage must be between 5 and 500 charcters")
//    private String message;
}
