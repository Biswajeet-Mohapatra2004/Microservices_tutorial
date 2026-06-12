package com.microservices.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Schema(
        name = "The default schema for loans entity",
        description = "The schema holds all the information related to loans"
)
@Data
public class LoansDTO {

    @Schema(description = "The Mobile number associated witht the loan",example = "1234567890")
    @NotEmpty(message = "Mobile Number cannot be null or empty")
    @Pattern(regexp = "(^[0-9]{10}$)",message = "Mobile number must be of 10 digits")
    private String mobileNumber;

    @Schema(description = "The loan number of the loan entity",example = "120012004343")
    @NotEmpty(message = "Loan Number cannot be null or empty")
    @Pattern(regexp = "(^[0-9]{12}$)",message = "Loan number must be of 12 digits")
    private String loanNumber;

    @Schema(description = "The type of loan, user is availing", example = "HOME_LOAN")
    @NotEmpty(message = "Loan type cannot be null or empty")
    private String loanType;

    @Schema(description = "The loan amount which the user is availing",example = "100000")
    @Positive(message = "Total loan amount should be greater then zero")
    private int totalLoan;

    @Schema(description = "The amount that has been repaid by the user",example = "10000")
    @PositiveOrZero(message = "Total amount paid should be greater then or equal to zero")
    private int amountPaid;

    @Schema(description = "The remaining amount of the loan",example = "90000")
    @PositiveOrZero(message = "Total amount paid should be greater then or equal to zero")
    private int outstandingAmount;
}
