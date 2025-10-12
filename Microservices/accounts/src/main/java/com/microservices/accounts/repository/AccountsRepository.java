package com.microservices.accounts.repository;

import com.microservices.accounts.model.Accounts;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts,Long> {

    Accounts findByCustomerId(long customerId);

    @Transactional // tells the server to roll back the changes if any error occurs during runtime
    @Modifying
    void deleteByCustomerId(long customerId);

    Optional<Accounts> findByAccountNumber(long accountNumber);
}
