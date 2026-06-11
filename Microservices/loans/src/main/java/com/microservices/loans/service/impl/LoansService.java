package com.microservices.loans.service.impl;

import com.microservices.loans.constants.LoansConstants;
import com.microservices.loans.dto.LoansDTO;
import com.microservices.loans.entity.Loans;
import com.microservices.loans.exception.LoanAlreadyExistException;
import com.microservices.loans.exception.ResourceNotFoundException;
import com.microservices.loans.mapper.LoansMapper;
import com.microservices.loans.repository.LoansRepository;
import com.microservices.loans.service.ILoansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class LoansService implements ILoansService {

    @Autowired
    LoansRepository loansRepository;

    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loans> optionalLoans=loansRepository.findByMobileNumber(mobileNumber);
        if(optionalLoans.isPresent()){
            throw new LoanAlreadyExistException("Loan already registered with given mobile number: "+mobileNumber);
        }
        loansRepository.save(createNewLoan(mobileNumber));
    }

    private Loans createNewLoan(String mobileNumber) {
        Loans loans=new Loans();
        long randomLoanNumber=100000000000L + new Random().nextInt(900000000);
        loans.setLoanNumber(Long.toString(randomLoanNumber));
        loans.setMobileNumber(mobileNumber);
        loans.setLoanType(LoansConstants.HOME_LOAN);
        loans.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        loans.setAmountPaid(0);
        loans.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        return loans;
    }

    @Override
    public LoansDTO fetchLoan(String mobileNumber) {
        Optional<Loans> existingLoan=loansRepository.findByMobileNumber(mobileNumber);
        if(existingLoan.isEmpty()){
            throw new ResourceNotFoundException("loans","mobileNumber",mobileNumber);
        }
        return LoansMapper.mapToLoansDTO(existingLoan.get(),new LoansDTO());
    }

    @Override
    public boolean updateLoan(LoansDTO loansDTO) {
        Loans loans=loansRepository.findByLoanNumber(loansDTO.getLoanNumber());
        if(loans==null){
            throw new ResourceNotFoundException("loans","LoanNumber",loansDTO.getLoanNumber());
        }
        loansRepository.save(LoansMapper.mapToLoans(loansDTO, loans));
        return true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        Optional<Loans> loans = loansRepository.findByMobileNumber(mobileNumber);
        if(loans.isEmpty()){
            throw new ResourceNotFoundException("Loans","mobileNumber",mobileNumber);
        }
        loansRepository.deleteById(loans.get().getLoanId());
        return true;
    }
}
