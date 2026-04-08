package com.eazybank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name="Error Response",
        description = "Schema to hold the Error Response Information."
)
public class ErrorResponseDto {

    @Schema(
            description = "Api path way invoke by client."
    )
    private String apiPath;

    @Schema(
            description = "Error code representing the error happened"
    )
    private HttpStatus errorCode;

    @Schema(
            description = "Error message representing the error happened"
    )
    private String errorMessage;

    @Schema(
            description = "Time representing when the error happened"
    )
    private LocalDateTime errorTime;

}
