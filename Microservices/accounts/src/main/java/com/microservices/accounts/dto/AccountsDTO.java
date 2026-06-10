package com.microservices.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data // provides the getter setter and toString()
//swagger annotation to provide name to entities
@Schema(
        name = "Account",
        description = "Schema to hold account information"
)
public class AccountsDTO {

    @Schema(
            description = "Account number of the customer", example = "1234bhzsdgv1212"
    )
    @NotEmpty(message = "Account number cannot be null.")
    @Pattern(regexp = "($|[0-9]{10})",message = "Account number must be of 10 digits.")
    private long accountNumber;

    @Schema(
            description = "Account type of the customer", example = "Savings"
    )
    @NotEmpty(message = "Account type cannot be null or empty.")
    private String accountType;

    @Schema(
            description = "Branch of the Bank where the account was created", example = "123_street_5th_Avenue_NYC_New_York_US"
    )
    @NotEmpty(message = "Branch address cannot be null or empty.")
    private String branchAddress;


}
