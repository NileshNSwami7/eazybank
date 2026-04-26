package com.eazybank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.rmi.registry.Registry;

@Data
@Schema(
        name="Accounts",
        description = "Schema to hold Account  information."
)
public class AccountsDto {
    @NotEmpty(message = "Account number can not be null or empty.")
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number should be 10 digit.")
    @Schema(
            description = "Account number of eazyBank account", example = "0123456789"
    )
    private Long accountNumber;

    @NotEmpty(message = "Account number can not be null or empty.")
    @Schema(
            description = "Account type of eazyBank account." , example = "Saving"
    )
    private String accountType;

    @NotEmpty(message="Branch Address can not be null.")
    @Schema(
            description = "Branch address of eazyBank account", example = "123 Street,pune"
    )
    private String branchAddress;
}

