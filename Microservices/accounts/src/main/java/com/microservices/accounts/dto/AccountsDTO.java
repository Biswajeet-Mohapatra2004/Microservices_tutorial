package com.microservices.accounts.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data // provides the getter setter and toString()
public class AccountsDTO {

    @NotEmpty(message = "Account number cannot be null.")
    @Pattern(regexp = "($|[0-9]{10})",message = "Account number must be of 10 digits.")
    private long accountNumber;

    @NotEmpty(message = "Account type cannot be null or empty.")
    private String accountType;

    @NotEmpty(message = "Branch address cannot be null or empty.")
    private String branchAddress;


}
