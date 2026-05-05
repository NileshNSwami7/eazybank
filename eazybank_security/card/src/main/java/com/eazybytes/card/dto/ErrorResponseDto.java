package com.eazybytes.card.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Schema(
        name="ErrorResponse",
        description = "Schema to hold the Error Response information."
)
@Data
@AllArgsConstructor
public class ErrorResponseDto {

    @Schema(
            description = "API path invoked by client."
    )
    private String apiPath;

    @Schema(
            description = "Error code representing error happened"
    )
    private HttpStatus errorCode;

    @Schema(
            description = "Error message representing error happened."
    )
    private String errorMessage;

    @Schema(
            description = "Time representing when th error happened."
    )
    private LocalDateTime errorTime;
}
