package com.eazybytes.loans.mapper;

import com.eazybytes.loans.dto.LoansDto;
import com.eazybytes.loans.entity.Loans;

public class LoansMapper {

    public static LoansDto mapToLoansDto(Loans loans, LoansDto loansDto){
        loansDto.setLoanNumber(loans.getLoanNumber());
        loansDto.setMobileNumber(loans.getMobileNumber());
        loansDto.setLoanType(loans.getLoanType());
        loansDto.setTotalLoan(loans.getTotalLoan());
        loansDto.setAmountPaid(loans.getAmountPaid());
        loansDto.setOutstandingAmount(loans.getOutstandingAmount());
        return loansDto;
    }
    public static Loans mapToLoans(LoansDto loandDto, Loans loan){
        loan.setLoanNumber(loandDto.getLoanNumber());
        loan.setMobileNumber(loandDto.getMobileNumber());
        loan.setLoanType(loandDto.getLoanType());
        loan.setTotalLoan(loandDto.getTotalLoan());
        loan.setAmountPaid(loandDto.getAmountPaid());
        loan.setOutstandingAmount(loandDto.getOutstandingAmount());
        return loan;
    }
}
