package com.eazybank.accounts.services.impl;

import com.eazybank.accounts.constants.AccountsConstants;
import com.eazybank.accounts.dto.AccountMsgDto;
import com.eazybank.accounts.dto.AccountsDto;
import com.eazybank.accounts.dto.CustomerDto;
import com.eazybank.accounts.entity.Accounts;
import com.eazybank.accounts.entity.Customer;
import com.eazybank.accounts.exceptions.CustomerAlreadyExist;
import com.eazybank.accounts.exceptions.ResourceNotFoundException;
import com.eazybank.accounts.mapper.AccountsMapper;
import com.eazybank.accounts.mapper.CustomerMapper;
import com.eazybank.accounts.repository.AccountsRepository;
import com.eazybank.accounts.repository.CustomerRepository;
import com.eazybank.accounts.services.IAccountsService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountsService {

    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private final StreamBridge streamBridge;

    public void createAccount(CustomerDto customerDto){
    Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
    Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()){
            throw  new CustomerAlreadyExist("This customer already exist with the given mobile number "
                    + customerDto.getMobileNumber());
        }
        Customer savedCustomer = customerRepository.save(customer);
        Accounts savedAccount =  accountsRepository.save(createAccount(savedCustomer));
        sendCommunication(savedAccount, savedCustomer);
    }

    private void sendCommunication(Accounts accounts, Customer customer){
        var accountsMsgsDto = new AccountMsgDto(accounts.getAccountNumber(),customer.getName(),
                customer.getEmail(),customer.getMobileNumber());
        log.info("Sending Communication request for the clients: {}", accountsMsgsDto);
        var result = streamBridge.send("sendingCommunication-out-0", accountsMsgsDto);
        log.info("Is the Communication request successfully triggered ? : {} ",result);

    }
    private Accounts createAccount(Customer customer){
        Accounts account = new Accounts();
        account.setCustomerId(customer.getCustomerId());
        long acntNumber = 1000000000L + new Random().nextInt(900000000);
        account.setAccountNumber(acntNumber);
        account.setAccountType(AccountsConstants.SAVINGS);
        account.setBranchAddress(AccountsConstants.ADDRESS);
        return account;
    }


    public CustomerDto fetchAccountDetails(String mobileNumber){
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).
                orElseThrow(()->new ResourceNotFoundException("Customer","mobileNumber",mobileNumber));
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).
                orElseThrow(()->new ResourceNotFoundException("Account","CustomerId",customer.getCustomerId().toString()));

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer,new CustomerDto());
        customerDto.setAccountDto(AccountsMapper.mapToAccountsDto(accounts,new AccountsDto()));
        return customerDto;
    }

    public boolean updateAccount(CustomerDto customerDto){
        boolean isUpdated = false;
        AccountsDto accountDto = customerDto.getAccountDto();
        if(accountDto != null){
            Accounts account = accountsRepository.findById(accountDto.getAccountNumber())
                    .orElseThrow(()->new ResourceNotFoundException
                            ("Account","AccontNumber",accountDto.getAccountNumber().toString()));
        AccountsMapper.mapToAccounts(accountDto,account);
        account=accountsRepository.save(account);

        Long customerId = account.getCustomerId();
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(()->new ResourceNotFoundException("customer","customerId",customerId.toString()));
        CustomerMapper.mapToCustomer(customerDto,customer);
        customerRepository.save(customer);
        isUpdated = true;
        }
        return isUpdated;
    }

    public boolean deleteAccountDetails(String mobileNumber){

        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(()->new ResourceNotFoundException("Customer","mobileNumber",mobileNumber));
        accountsRepository.deleteBycustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }
}
