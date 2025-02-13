package com.example.demo.dto;

import com.example.demo.entity.Account;
import com.example.demo.entity.AccountType;  // Assuming AccountType is an enum
// import com.example.demo.entity.AccountType;
import java.math.BigDecimal;
import jakarta.validation.constraints.NotNull;

public class AccountDTO {
    private Long id;
    private Long userId;
    @NotNull(message = "Account number should not be null")
    private String accountNumber;
    @NotNull(message = "Balance should not be null")
    private BigDecimal balance;
    private AccountType accountType;  // Added accountType

    
    // Constructor accepting Account entity
    public AccountDTO(Account account) {
        this.id = account.getId();
        this.userId = account.getUser().getId();
        this.accountNumber = account.getAccountNumber();
        this.balance = account.getBalance();
        this.accountType = account.getAccountType();  // Mapping accountType from Account entity
    }

    // Default constructor
    public AccountDTO() {}
   

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public AccountType getAccountType() {  // Added getter for accountType
        return accountType;
    }

}
