package com.microservices.accounts.service;

import com.microservices.accounts.dto.CustomerDTO;
import org.springframework.stereotype.Component;

@Component
public interface IAccountsService {

    /**
     * this method is implemented to create new user accounts
     * @param customerDTO- CustomerDto object
     */
    void createAccount(CustomerDTO customerDTO);

    /**
     * this method is implemented to create new user accounts
     * @param mobileNumber- Input mobile number
     * @return Accounts details based on a given mobile number.
     */
    CustomerDTO fetchAccount(String mobileNumber);

    /**
     * @param customerDTO- CustomerDTO object
     * @return boolean indicating if the update of account details is successful or not
     */
    boolean updateAccount(CustomerDTO customerDTO);

    /**
     * @param mobileNumber- String mobile number of the user
     * @return boolean indicating if the update of account details is successful or not
     */
    boolean deleteAccount(String mobileNumber);

}
