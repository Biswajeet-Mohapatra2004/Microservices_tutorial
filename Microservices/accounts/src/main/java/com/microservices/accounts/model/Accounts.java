package com.microservices.accounts.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString
public class Accounts extends BaseEntity{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="account_id")
    private long accountId;

    @Column(name = "customer_id")
    private long customerId;

    @Column(name = "account_number")
    private long accountNumber; // we won't be generating sequential account numbers

    @Column(name="account_type")
    private String accountType;

    @Column(name="branch_address")
    private String branchAddress;

}
