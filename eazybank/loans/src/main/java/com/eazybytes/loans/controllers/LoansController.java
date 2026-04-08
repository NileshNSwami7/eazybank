package com.eazybytes.loans.controllers;

import com.eazybytes.loans.constants.LoansConstants;
import com.eazybytes.loans.dto.ErrorResponseDto;
import com.eazybytes.loans.dto.LoansDto;
import com.eazybytes.loans.dto.ResponseDto;
import com.eazybytes.loans.service.LoanServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/eazybank/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class LoansController {

    private LoanServices iloanServices;

    @Operation(
            summary = "Create Loan REST API",
            description = "REST API to create new loan inside eazybank."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Staus created."
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status INTERNAL SERVER ERROR",
                    content=@Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto>createLoans(@RequestParam
                                                      @Pattern(regexp = "(^$|[0-9]{10})", message="Mobile number must be 10 digit.")String mobileNumber){

        iloanServices.createLoans(mobileNumber);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(LoansConstants.STATUS_201,LoansConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Fetch Loans details REST API.",
            description="Fetch the loans details using mobile number."
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
                            schema=@Schema(
                                implementation = ErrorResponseDto.class
                            )
    )
            )
    })
    @GetMapping("/fetch")
    public ResponseEntity<LoansDto>fetchLoansDetails(@RequestParam
                                                        @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be 10 digit.")
                                                        String mobileNumber){
        LoansDto loansDto = iloanServices.fetchLoansDetails(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loansDto);
    }
}
