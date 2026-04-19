package com.eazybank.accounts.services.client;

import com.eazybank.accounts.dto.CardsDto;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("cards")
public interface CardsFeignClient {

    @GetMapping(value="/eazybank/api/fetch", consumes="application/json")
    public ResponseEntity<CardsDto> fetchCardsDetails(@RequestParam String mobileNumber);
}
