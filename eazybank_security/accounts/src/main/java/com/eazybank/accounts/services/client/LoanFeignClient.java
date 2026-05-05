package com.eazybank.accounts.services.client;

import com.eazybank.accounts.dto.LoansDto;
import jakarta.validation.constraints.Pattern;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="loans",fallback = LoansFallback.class)
public interface LoanFeignClient {

    @GetMapping(value = "/eazybank/api/fetch",consumes = "application/json")
    public ResponseEntity<LoansDto> fetchLoansDetails(
            @RequestHeader("eazybank-correlation-id") String correlationId,
            @RequestParam String mobileNumber);
}
