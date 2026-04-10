package com.eazybytes.card.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
@Schema(
        name="Cards",
        description = "Schema to hold the Cards information."
)
public class CardsDto {

    @NotEmpty(message = "Mobile number should not be null or empty.")
    @Pattern(regexp="(^$|[0-9]{10})", message = "Mobile number must be 10 digits.")
    @Schema(
            description = "Mobile number of Customer", example = "0123456789"
    )
    private String mobileNumber;

    @NotEmpty(message = "Card number should not be null or empty.")
    @Pattern(regexp = "(^$|[0-9]{12})",message = "Card number must be 12 digits.")
    @Schema(
            description = "Card number of the  customer.", example = "012936547191"
    )
    private String cardNumber;

    @NotEmpty(message = "Card should not be null or empty.")
    @Schema(
            description = "Type of the card", example = "Credit card."
    )
    private String cardType;

    @Positive(message = "Total limit should be greater than zero.")
    @Schema(
            description = "Total amount limit available against card.", example = "50000"
    )
    private int totalLimit;

    @PositiveOrZero(message = "The amount used should be equal to greater than zero.")
    @Schema(
            description = "Total amount used by customer.", example = "4000"
    )
    private int amountUsed;

    @PositiveOrZero(message = "The available amount shoul be equal to greater than zero.")
    @Schema(
            description = "Total available amount against card. ",example = "46000"
    )
    private int availableAmount;

}
