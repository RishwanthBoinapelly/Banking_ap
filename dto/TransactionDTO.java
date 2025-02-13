package com.example.demo.dto;
import com.example.demo.dto.TransactionDTO;
// import com.example.demo.entity.AccountType;
import com.example.demo.entity.Transaction;
import com.example.demo.entity.TransactionType;
// import com.example.demo.entity.TransactionStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
// import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

// import com.example.demo.entity.TransactionType;
// import com.example.demo.repository.TransactionRepository;
public class TransactionDTO {
    private Long id;
    private Long accountId;
    private BigDecimal amount;
    private TransactionType transactionType;
    private LocalDate transactionDate;
    private String status;
    // Constructor accepting Transaction entity
    @Autowired
// private TransactionRepository transactionRepository;

    public TransactionDTO() {}
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getAccountId() {
        return accountId;
    }
    
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public TransactionType getTransactionType() {
        return transactionType;
    }
    
    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
    
    public LocalDate getTransactionDate() {
        return transactionDate;
    }
    
    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.accountId = (transaction.getAccount() != null) ? transaction.getAccount().getId() : null;
        this.amount = transaction.getAmount();
        this.transactionType = transaction.getTransactionType();
        this.transactionDate = transaction.getDate().toLocalDate();
    }
    // @Override
    
    // public TransactionDTO updateTransaction(Long transactionId, TransactionDTO transactionDTO) {
    //     Transaction transaction = transactionRepository.findById(transactionId)
    //             .orElseThrow(() -> new NotFoundException("Transaction not found with id: " + transactionId));
    
    //     transaction.setAmount(transactionDTO.getAmount());
    //     transaction.setTransactionType(transactionDTO.getTransactionType());
    //     Transaction updatedTransaction = transactionRepository.save(transaction);

    //     return new TransactionDTO(updatedTransaction);
    // }

}
