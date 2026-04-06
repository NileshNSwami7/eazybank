package com.eazybank.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.rmi.registry.Registry;

@Data
public class AccountsDto {
    @NotEmpty(message = "Account number can not be null or empty.")
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number should be 10 digit.")
    private Long accountNumber;
    @NotEmpty(message = "Account number can not be null or empty.")
    private String accountType;
    @NotEmpty(message="Branch Address can not be null.")
    private String branchAddress;
}

