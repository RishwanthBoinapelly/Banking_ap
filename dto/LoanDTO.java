package com.example.demo.dto;

import com.example.demo.entity.Loan;
import com.example.demo.entity.LoanStatus;
import com.example.demo.entity.LoanType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;

public class LoanDTO {

    private Long id;

    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    @NotNull(message = "Interest rate is required")
    private Double interestRate;

    @NotNull(message = "Issue date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate issueDate;

    @NotNull(message = "Loan status is required")
    private LoanStatus loanStatus;

    private Long userId;

    @NotNull(message = "Loan type must not be null")
    @Enumerated(EnumType.STRING)
    private LoanType loanType;
    private Long accountId;

    // Default Constructor for JSON (important for Jackson)
    public LoanDTO() {}

    // Constructor with All Required Fields
    public LoanDTO(Long id, BigDecimal amount, Double interestRate, LocalDate issueDate, LoanStatus loanStatus) {
        this.id = id;
        this.amount = amount;
        this.interestRate = interestRate;
        this.issueDate = issueDate;
        this.loanStatus = loanStatus;
    }

    public LoanDTO(Loan loan) {
        this.id = loan.getId();
        this.userId = loan.getUser() != null ? loan.getUser().getId() : null;
        this.amount = loan.getAmount();
        this.interestRate = loan.getInterestRate();
        this.loanStatus = loan.getLoanStatus();
        this.issueDate = loan.getIssueDate();
        this.loanType = loan.getLoanType();
        this.accountId = loan.getAccount() != null ? loan.getAccount().getId() : null; // Set accountId
    }

    // Getters and Setters
    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
    
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LoanStatus getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(LoanStatus loanStatus) {
        this.loanStatus = loanStatus;
    }

    public LoanType getLoanType() {
        return loanType;
    }

    public void setLoanType(LoanType loanType) {
        this.loanType = loanType;
    }
}
