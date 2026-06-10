package com.microservices.loans.service.impl;

import com.microservices.loans.dto.LoansDTO;
import com.microservices.loans.service.ILoansService;

public class LoansService implements ILoansService {
    @Override
    public void createLoan(String mobileNumber) {

    }

    @Override
    public LoansDTO fetchLoan(String mobileNumber) {
        return null;
    }

    @Override
    public boolean updateLoan(LoansDTO loansDTO) {
        return false;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        return false;
    }
}
