package com.eazybank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Schema(
        name = "Customer",
        description = "Schema to hold the Customer Information."
)
public class CustomerDto {
    @NotEmpty(message="Name can not be null or Empty.")
    @Size(min=5,max=30,message="The length of the customer name should be between 5 and 30.")
    @Schema(
            description = "Name of customer in eazyBank account.", example = "Raghav"
    )
    private String name;

    @NotEmpty(message="Email address can to be null or empty.")
    @Email(message="Email address should be a valid value.")
    @Schema(
            description = "Email of customer in eazyBank account.", example = "xyz@eazybank.com"
    )
    private String email;

    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number should be 10 digit.")
    @Schema(
            description = "Mobile number of customer in eazyBank account", example = "9023845675"
    )
    private String mobileNumber;
    @Schema(
            description = "Schema to hold Accounts details of eazyBank"
    )
    private AccountsDto accountDto;
}
