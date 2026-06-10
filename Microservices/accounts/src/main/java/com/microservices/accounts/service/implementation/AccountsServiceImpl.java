package com.microservices.accounts.service.implementation;

import com.microservices.accounts.constants.AccountsConstants;
import com.microservices.accounts.dto.AccountsDTO;
import com.microservices.accounts.dto.CustomerDTO;
import com.microservices.accounts.exception.CustomerAlreadyExistException;
import com.microservices.accounts.exception.ResourceNotFoundException;
import com.microservices.accounts.mapper.AccountsMapper;
import com.microservices.accounts.mapper.CustomerMapper;
import com.microservices.accounts.model.Accounts;
import com.microservices.accounts.model.Customer;
import com.microservices.accounts.repository.AccountsRepository;
import com.microservices.accounts.repository.CustomerRepository;
import com.microservices.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * this method is implemented to create new user accounts
     *
     * @param customerDTO - CustomerDto object
     */
    @Override
    public void createAccount(CustomerDTO customerDTO) {
        Customer customer= CustomerMapper.mapToCustomer(customerDTO,new Customer());
        Optional<Customer> optionalCustomer=customerRepository.findByMobileNumber(customerDTO.getMobileNumber());
        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistException("Customer already registered with given mobile number "+customerDTO.getMobileNumber());
        }
        Customer response=customerRepository.save(customer);
        accountsRepository.save(createNewAccount(response));
    }

    private Accounts createNewAccount(Customer customer){
        Accounts accounts=new Accounts();
        accounts.setCustomerId(customer.getCustomerId());
        long randomAccNumber=1000000000L + new Random().nextInt(900000000);

        accounts.setAccountNumber(randomAccNumber);
        accounts.setAccountType(AccountsConstants.SAVINGS);
        accounts.setBranchAddress(AccountsConstants.ADDRESS);
        return accounts;
    }

    /**
     * this method is implemented to create new user accounts
     *
     * @param mobileNumber - Input mobile number
     * @return Accounts details based on a given mobile number.
     */
    @Override
    public CustomerDTO fetchAccount(String mobileNumber) {
        Optional<Customer> customer=customerRepository.findByMobileNumber(mobileNumber);
        if(customer.isEmpty()) {
            throw new ResourceNotFoundException("Customer", "MobileNumber", mobileNumber);
        }
        Accounts accounts=accountsRepository.findByCustomerId(customer.get().getCustomerId());
        CustomerDTO customerDTO=CustomerMapper.mapToCustomerDto(customer,new CustomerDTO());
        AccountsDTO accountsDTO= AccountsMapper.mapToAccountsDTO(accounts,new AccountsDTO());
        customerDTO.setAccountsDTO(accountsDTO);
        return customerDTO;
    }

    /**
     * @param customerDTO - CustomerDTO object
     * @return boolean indicating if the update of account details is successful or not
     */
    @Override
    public boolean updateAccount(CustomerDTO customerDTO) {
        boolean isUpdated=false;
        AccountsDTO accountsDTO=customerDTO.getAccountsDTO();
        if(accountsDTO!=null){
           Optional<Accounts> accounts=accountsRepository.findByAccountNumber(accountsDTO.getAccountNumber());
            if(accounts.isEmpty()){
                throw new ResourceNotFoundException("Account","Account Number", String.valueOf(accountsDTO.getAccountNumber()));
            }
            Accounts updated=AccountsMapper.mapToAccounts(accountsDTO,accounts.get());
            accountsRepository.save(updated);

            Long customerId=accounts.get().getCustomerId();
            Optional<Customer> customer=customerRepository.findById(customerId);
            if(customer.isEmpty()){
                throw new ResourceNotFoundException("Customer","Customer ID",customerId.toString());
            }
            Customer updatedCustomer=CustomerMapper.mapToCustomer(customerDTO,customer.get());
            customerRepository.save(updatedCustomer);
            isUpdated=true;
        }
        return isUpdated;
    }

    /**
     * @param mobileNumber - String mobile number of the user
     * @return boolean indicating if the update of account details is successful or not
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {

        Optional<Customer> customer=customerRepository.findByMobileNumber((mobileNumber));
        if(customer.isEmpty()){{
            throw new ResourceNotFoundException("Customer","Mobile Number",mobileNumber);
        }}
        accountsRepository.deleteByCustomerId(customer.get().getCustomerId());
        customerRepository.deleteById(customer.get().getCustomerId());
        return true;
    }


}
