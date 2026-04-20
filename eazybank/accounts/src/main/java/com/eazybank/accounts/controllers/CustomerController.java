package com.eazybank.accounts.controllers;

import com.eazybank.accounts.dto.CustomerDetailsDto;
import com.eazybank.accounts.services.ICustomerService;
import com.eazybank.accounts.services.impl.ICustomerServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name="CRUD REST APIs for Customer in eazyBank. ",
        description="REST API in eazybank to fetch customer details"
)
@RestController
@RequestMapping(path="/eazybank/api", produces={MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final ICustomerService iCustomerService;

    public CustomerController(ICustomerService iCustomerService) {
        this.iCustomerService = iCustomerService;
    }

    @Operation(
            summary = "FETCH Customer details REST API",
            description ="REST API to FETCH the customer details Based on Mobile number."
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
    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> fetCustomerDetails(
                                                                     @RequestHeader("eazbank-correlation-id") String correlationID,
                                                                     @RequestParam
                                                                     @Pattern(regexp="($^|[0-9]{10})",message = "Mobile number must be 10 digit.")
                                                                     String mobileNumber){
        logger.debug("eazybank-correlation-id found: {} ", correlationID);
        CustomerDetailsDto customerDetailsDto=iCustomerService.fetchCustomerDetails(mobileNumber,correlationID);
        return ResponseEntity.status(HttpStatus.OK)
            .body(customerDetailsDto);
    }
}
