package com.eazybytes.loans.dto;



import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Schema(
            name="Loans",
            description = "Schema to hold the loans information."
)
@Data
public class LoansDto {

    @NotEmpty(message="Mobile number should not be empty.")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits.")
    @Schema(
            description = "Mobile number of customer.", example = "0123456789"
    )
    private String mobileNumber;

    @NotEmpty(message = "Loan number should not be empty.")
    @Pattern(regexp = "(^$|[0-9]{12})", message = "Loan number must be 12 digit.")
    @Schema(
            description = "Loan number of customer.", example="283651876232"
    )
    private String loanNumber;

    @NotEmpty(message = "Loan type should not be null or empty.")
    @Schema(
            description="Type of the loan", example = "Home Loan"
    )
    private String loanType;

    @Positive(message = "Total loan should be greater than zero.")
    @Schema(
            description = "total loan of customer", example = "900000"
    )
    private int totalLoan;

    @PositiveOrZero(message = "The loan amount paid should be equal or greater than zero")
    @Schema(
            description = "The total loan amount paid", example = "800000"
    )
    private int amountPaid;

    @PositiveOrZero(message = "The outstanding amount should be equal or greater than zero.")
    @Schema(
            description = "Total outstanding amount against a loan", example="100000"
    )
    private int outstandingAmount;
}
