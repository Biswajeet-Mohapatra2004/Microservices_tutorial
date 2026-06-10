package com.microservices.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data// provides the getter setter and toString()
@Schema(
        name = "Customer",
        description = "Schema to hold customer information"
)
public class CustomerDTO {

    @Schema(
            description = "The name of the customer", example = "John Doe"
    )
    @NotEmpty(message="Name cannot be empty.") // input validation
    @Size(min=3,max = 30,message = "Name should be between 3 and 30.")
    private String name;

    @Schema(
            description = "The email of the customer", example = "JohnDoe@example.com"
    )
    @NotEmpty(message = "Email cannot be null.")
    @Email(message = "Email address should be a valid value.")
    private String email;

    @Schema(
            description = "The contact number of the customer", example = "92309230xx"
    )
    @NotEmpty(message = "phone number cannot be null.")
    @Pattern(regexp = "($|[0-9]{10})",message = "Number must be of 10 digits.")
    private String mobileNumber;

    @Schema(
            description = "The account associated with the customer", example = "Account{}"
    )
    private AccountsDTO accountsDTO;
}
