package com.eazybank.accounts.controllers;

import com.eazybank.accounts.constants.AccountsConstants;
import com.eazybank.accounts.dto.CustomerDto;
import com.eazybank.accounts.dto.ResponseDto;
import com.eazybank.accounts.services.IAccountsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/eazybank/api", produces={MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class AccountsController {

    private IAccountsService accountService;

    @PostMapping("/createAccount")
    public ResponseEntity<ResponseDto>createAccount(@Valid @RequestBody  CustomerDto customerDto){
        accountService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto>getCustomerDetail(@Pattern (regexp = "(^$|[0-9]{10})", message="Mobile number should be 10 digit.")
                                                            @RequestParam String mobileNumber){
       CustomerDto customerDto =  accountService.fetchAccountDetails(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @PutMapping("/update")
    public ResponseEntity<CustomerDto>updateAccountDetails(@Valid @RequestBody CustomerDto customerDto){
        boolean isUpdated = accountService.updateAccount(customerDto);
        if(isUpdated)
        {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(customerDto);
        }else{
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(customerDto);
        }
    }
@DeleteMapping("/delete")
    public ResponseEntity<ResponseDto>deleteAccountDetails(@Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number should be 10 digit.")
                                                           @RequestParam String mobileNumber){
        boolean isDeleted = accountService.deleteAccountDetails(mobileNumber);
        if(isDeleted){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_417,AccountsConstants.MESSAGE_417_DELETE));
        }
    }
}
