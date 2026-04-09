package com.eazybytes.loans.service;

import com.eazybytes.loans.dto.LoansDto;
import org.springframework.stereotype.Service;


public interface LoanServices {

    void createLoans(String mobileNumber);

    LoansDto fetchLoansDetails(String mobileNumber);

    boolean updateLoanDetails(LoansDto loansDto);

    boolean deleteLoans(String mobileNumber);
}
