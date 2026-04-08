package com.eazybytes.loans.service.impl;

import com.eazybytes.loans.constants.LoansConstants;
import com.eazybytes.loans.entity.Loans;
import com.eazybytes.loans.exception.LoanAlreadyExistException;
import com.eazybytes.loans.repository.LoansRepository;
import com.eazybytes.loans.service.LoanServices;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.Random;

@Service
@Repository
@AllArgsConstructor
public class LoanServiceImpl  implements LoanServices {

    private LoansRepository loansRepository;

    public void createLoans(String mobileNumber){
        Optional<Loans> optional = loansRepository.findByMobileNumber(mobileNumber);
        if(optional.isPresent()){
            throw  new LoanAlreadyExistException("Loan already registered with the given mobile number"+mobileNumber);
        }
        loansRepository.save(createNewLoans(mobileNumber));
    }

    private Loans createNewLoans(String mobileNumber){
        Loans newloan = new Loans();
        long randomLoanNumber = 10000000000L + new Random().nextInt(900000000);
        newloan.setLoanNumber(Long.toString(randomLoanNumber));
        newloan.setMobileNumber(mobileNumber);
        newloan.setLoanType(LoansConstants.HOME_LOAN);
        newloan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newloan.setAmountPaid(0);
        newloan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        return newloan;
    }
}