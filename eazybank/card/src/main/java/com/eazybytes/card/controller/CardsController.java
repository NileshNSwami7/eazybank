package com.eazybytes.card.controller;

import com.eazybytes.card.constants.CardsConstants;
import com.eazybytes.card.dto.CardsDto;
import com.eazybytes.card.dto.ErrorResponseDto;
import com.eazybytes.card.dto.ResponseDto;
import com.eazybytes.card.service.CardsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.aspectj.bridge.IMessage;
import org.hibernate.stat.CacheableDataStatistics;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/eazybank/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class CardsController {

    private CardsService icardService;

    @PostMapping("/create")
    @Operation(
            summary = "CREATE card REST API",
            description = "REST API to create new Card inside eazybank."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ErrorResponseDto.class
                            )
                    )
            )
    })
    public ResponseEntity<ResponseDto>createCards(@RequestParam
                                                 @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be 10 digits.")
                                                 String mobileNumber){
        icardService.createCard(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(CardsConstants.STATUS_201,CardsConstants.MESSAGE_201));
    }
    @Operation(
            summary = "REST API to fetch the cards details.",
            description = "REST API to fetch the card details based on mobile number."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content =@Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/fetch")
    public ResponseEntity<CardsDto>fetchCardsDetails(@RequestParam
                                                             @Pattern(regexp="(^$|[0-9]{10})", message="Mobile number must be 10 digits.")
                                                             String mobileNumber){
        CardsDto cardDto = icardService.fetchcardsDetials(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK)
                .body(cardDto);
    }

    
}
