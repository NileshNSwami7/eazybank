package com.eazybank.accounts.services;

import com.eazybank.accounts.dto.CustomerDetailsDto;

public interface ICustomerService {

     CustomerDetailsDto fetchCustomerDetails(String mobileNumber,String correlationID);
}
