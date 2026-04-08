package com.eazybytes.loans.dto;



import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
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
    @Pattern(regexp = "(^$|[0-9]{12})", message = "Loan number must be 12 digit")
    @Schema(
            description = "Loan number of customer.", example="283651876232"
    )
    private String loanNumber;

    @NotEmpty(message = "Loan type should not be null or empty.")
    @Schema(
            description="Type of the loan", example = "Home Loan"
    )
    private String loanType;

    @Positive(message = "Total loan should not be greater than or equal to zero.")
    @Schema(
            description = "total loan of customer", example = "900000"
    )
    private int totalLoan;

    @Positive(message = "Amount should not be greater than equal to zero")
    @Schema(
            description = "Amount paid by customer", example = "800000"
    )
    private int amountPaid;

    @Positive(message = "Outstanding amount should not be greater than equal to zero.")
    @Schema(
            description = "Total outstanding amount against loan", example="100000"
    )
    private int outstandingAmount;
}
