package com.eazybank.accounts.services.client;

import com.eazybank.accounts.dto.CardsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardsFallback implements  CardsFeignClient{
    @Override
    public ResponseEntity<CardsDto> fetchCardsDetails(String correlationID, String mobileNumber) {
        return null;
    }
}
