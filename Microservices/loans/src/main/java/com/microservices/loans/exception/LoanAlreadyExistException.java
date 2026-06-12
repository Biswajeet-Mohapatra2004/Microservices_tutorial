package com.microservices.loans.exception;

public class LoanAlreadyExistException extends RuntimeException{
    public LoanAlreadyExistException(String message){
        super(message);
    }
}
