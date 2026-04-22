package com.eazybank.accounts.controllers;

import com.eazybank.accounts.constants.AccountsConstants;
import com.eazybank.accounts.dto.*;
import com.eazybank.accounts.services.IAccountsService;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;import io.swagger.v3.oas.annotations.media.Content;import io.swagger.v3.oas.annotations.media.Schema;import io.swagger.v3.oas.annotations.responses.ApiResponse;import io.swagger.v3.oas.annotations.responses.ApiResponses;import io.swagger.v3.oas.annotations.tags.Tag;import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Sort;import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name="CRUD REST APIs for Accounts in eazyBank. ",
        description="CRUD REST APIs for accounts in eazybank to CREATE, UPDATE, FETCH And DELETE accounts details."
)
@RestController
@RequestMapping(path="/eazybank/api", produces={MediaType.APPLICATION_JSON_VALUE})

@Validated
public class AccountsController {

    private static final Logger logger = LoggerFactory.getLogger(AccountsController.class);
    private final IAccountsService accountService;

    public AccountsController(IAccountsService accountService) {
        this.accountService = accountService;
    }
    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private AccountsContactInfoDto accountsContactInfoDto;

    @Operation(
            summary="Create Account REST API",
            description = "REST API to create new account of customer and Account in eazyBank."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status Internal Server Error"
            )}
    )
    @PostMapping("/createAccount")
    public ResponseEntity<ResponseDto>createAccount(@Valid @RequestBody  CustomerDto customerDto){
        accountService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @Operation(
            summary = "FETCH Account details REST API",
            description ="REST API to FETCH the custoner and  account details Based on Mobile number."
    )

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status Ok"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error"
            )
    })
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto>getCustomerDetail(@Pattern (regexp = "(^$|[0-9]{10})", message="Mobile number should be 10 digit.")
                                                            @RequestParam String mobileNumber){
       CustomerDto customerDto =  accountService.fetchAccountDetails(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @Operation(
            summary = "UPDATE Account details  REST API ",
            description = "REST API to Update Customer and Account details based on account number"
    )

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status Ok"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description="Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Internal Server Error",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ErrorResponseDto.class
                            )
                    )
            )
    })
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

    @Operation(
            summary = "DELETE Account and Customer details REST API",
            description = "REST API to delete Customer and Account details based on mobile number."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "407",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
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

    @Operation(
            summary = "Fetch java version info REST API",
            description = "REST API to FETCH the java version info"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @Retry(name="getBuildInfo",fallbackMethod = "getBuildInfoFallback")
    @GetMapping("/build-info")
    public ResponseEntity<String>getBuildInfo(){
        logger.debug("getBuildInfo() method Invoked");
        throw new NullPointerException();
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(buildVersion);
    }

    public ResponseEntity<String >getBuildInfoFallback(Throwable throwable){
        logger.debug("getBuildInfoFallback() method invoked.");
        return ResponseEntity.status(HttpStatus.OK)
                .body("0.9");
    }
    @Operation(
            summary = "Fetch build info REST API",
            description = "REST API to FETCH the build version info"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/java-version")
    public ResponseEntity<String>getJavaversion(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(environment.getProperty("HOME_JAVA"));
    }

    @Operation(
            summary = "Get Contact Info",
            description = "Contact details info that can be reached out in case if any issue. "
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactInfoDto>getAAccountinfo(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountsContactInfoDto);
    }
}
