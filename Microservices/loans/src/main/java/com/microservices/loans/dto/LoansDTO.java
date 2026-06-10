package com.microservices.loans.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class LoansDTO {

    @NotEmpty(message = "Mobile Number cannot be null or empty")
    @Pattern(regexp = "(^$[0-9]{10})",message = "Mobile number must be of 10 digits")
    private String mobileNumber;

    @NotEmpty(message = "Loan Number cannot be null or empty")
    @Pattern(regexp = "(^$[0-9]{12})",message = "Loan number must be of 12 digits")
    private String loanNumber;

    @NotEmpty(message = "Loan type cannot be null or empty")
    private String loanType;

    @Positive(message = "Total loan amount should be greater then zero")
    private int totalLoan;

    @PositiveOrZero(message = "Total amount paid should be greater then or equal to zero")
    private int amountPaid;

    @PositiveOrZero(message = "Total amount paid should be greater then or equal to zero")
    private int outstandingAmount;
}
