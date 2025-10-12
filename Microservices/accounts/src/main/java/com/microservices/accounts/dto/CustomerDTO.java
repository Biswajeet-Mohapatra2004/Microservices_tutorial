package com.microservices.accounts.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data // provides the getter setter and toString()
public class CustomerDTO {

    @NotEmpty(message="Name cannot be empty.") // input validation
    @Size(min=3,max = 30,message = "Name should be between 3 and 30.")
    private String name;

    @NotEmpty(message = "Email cannot be null.")
    @Email(message = "Email address should be a valid value.")
    private String email;

    @NotEmpty(message = "phone number cannot be null.")
    @Pattern(regexp = "($|[0-9]{10})",message = "Number must be of 10 digits.")
    private String mobileNumber;

    private AccountsDTO accountsDTO;
}
