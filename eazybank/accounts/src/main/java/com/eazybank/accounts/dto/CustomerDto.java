package com.eazybank.accounts.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class CustomerDto {
    @NotEmpty(message="Name can not be null or Empty.")
    @Size(min=5,max=30,message="The length of the customer name should be between 5 and 30.")
    private String name;
    @NotEmpty(message="Email address can to be null or empty.")
    @Email(message="Email address should be a valid value.")
    private String email;
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number should be 10 digit.")
    private String mobileNumber;
    private AccountsDto accountDto;
}
