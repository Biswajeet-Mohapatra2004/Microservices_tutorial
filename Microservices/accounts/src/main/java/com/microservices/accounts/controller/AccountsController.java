package com.microservices.accounts.controller;

import com.microservices.accounts.constants.AccountsConstants;
import com.microservices.accounts.dto.CustomerDTO;
import com.microservices.accounts.dto.ResponseDTO;
import com.microservices.accounts.service.IAccountsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated // enables the controller to check for the model input validation
public class AccountsController {

    @Autowired
    private IAccountsService accountsService;

    @PostMapping(path="/create")
    public ResponseEntity<ResponseDTO> createAccount(@Valid @RequestBody CustomerDTO customerDTO){
        accountsService.createAccount(customerDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(AccountsConstants.SAVINGS,AccountsConstants.MESSAGE_201));

    }
    // here we are not accepting any DTO body so we can manually add input validation
    @GetMapping(path = "/fetch", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> fetchAccountDetails(@RequestParam("mobileNumber")
                                                               @Pattern(regexp = "($|[0-9]{10})",message = "mobile number cannot be empty or null.")
                                                               String mobileNumber){
        CustomerDTO customerDTO=accountsService.fetchAccount(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(customerDTO);
    }

    @PutMapping(path = "/update",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> updateAccountDetails(@Valid @RequestBody CustomerDTO customerDTO){
        boolean isUpdated=accountsService.updateAccount(customerDTO);
        if(isUpdated){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
        }
        else{
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(AccountsConstants.STATUS_500,AccountsConstants.MESSAGE_500));
        }
    }

    @DeleteMapping(path = "/delete",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> deleteAccount(@RequestParam("mobileNumber")
                                                         @Pattern(regexp = "($|[0-9]{10})",message = "mobile number cannot be empty or null.")
                                                         String mobileNumber){
        boolean isDeleted= accountsService.deleteAccount(mobileNumber);
        if(isDeleted){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
        }
        else{
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(AccountsConstants.STATUS_500,AccountsConstants.MESSAGE_500));

        }
    }
}
