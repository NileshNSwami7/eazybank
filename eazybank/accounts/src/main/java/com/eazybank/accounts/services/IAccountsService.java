package com.eazybank.accounts.services;

import com.eazybank.accounts.dto.CustomerDto;
import org.springframework.stereotype.Service;

@Service
public interface IAccountsService {

    void createAccount(CustomerDto costumerDto);
}
