package com.eazybank.accounts.services.impl;

import com.eazybank.accounts.dto.AccountsDto;
import com.eazybank.accounts.dto.CardsDto;
import com.eazybank.accounts.dto.CustomerDetailsDto;
import com.eazybank.accounts.dto.LoansDto;
import com.eazybank.accounts.entity.Accounts;
import com.eazybank.accounts.entity.Customer;
import com.eazybank.accounts.exceptions.ResourceNotFoundException;
import com.eazybank.accounts.mapper.AccountsMapper;
import com.eazybank.accounts.mapper.CustomerMapper;
import com.eazybank.accounts.repository.AccountsRepository;
import com.eazybank.accounts.repository.CustomerRepository;
import com.eazybank.accounts.services.ICustomerService;
import com.eazybank.accounts.services.client.CardsFeignClient;
import com.eazybank.accounts.services.client.LoanFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ICustomerServiceImpl implements ICustomerService {

    private AccountsRepository accountsrepository;
    private CustomerRepository customerrepository;
    private LoanFeignClient loanfeignclient;
    private CardsFeignClient cardsfeignclient;

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
        Customer customer = customerrepository.findByMobileNumber(mobileNumber).
                orElseThrow(()->new ResourceNotFoundException("customer", "mobileNumber", mobileNumber));

        Accounts accounts =  accountsrepository.findByCustomerId(customer.getCustomerId()).
                orElseThrow(()->new ResourceNotFoundException("accounts", "customerId", customer.getMobileNumber().toString()));

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer,new CustomerDetailsDto());
        customerDetailsDto.setAccountDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoansDto>fetchLoansDtoRepository = loanfeignclient.fetchLoansDetails(mobileNumber);
        customerDetailsDto.setLoansDto(fetchLoansDtoRepository.getBody());

        ResponseEntity<CardsDto>fetchCardsDtoRepository = cardsfeignclient.fetchCardsDetails(mobileNumber);
        customerDetailsDto.setCardsDto(fetchCardsDtoRepository.getBody());

        return customerDetailsDto;
    }
}
