package com.example.demo.Service.impl;

import com.example.demo.dto.TransactionDTO;
import com.example.demo.entity.Transaction;
import com.example.demo.entity.Account;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.repository.AccountRepository;
import com.example.demo.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.dto.TransferRequestDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    private final TransactionRepository transactionRepository;
    
    @Autowired
    private AccountRepository accountRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public String transferFunds(TransferRequestDTO transferRequest) {
        try {
            // Log the incoming request
            logger.info("Initiating transfer from account ID: {} to account ID: {} with amount: {}",
                    transferRequest.getFromAccountId(), transferRequest.getToAccountId(), transferRequest.getAmount());

            // Get the 'from' and 'to' accounts based on the provided account IDs
            Account fromAccount = accountRepository.findById(transferRequest.getFromAccountId())
                    .orElseThrow(() -> new IllegalArgumentException("From account not found"));

            Account toAccount = accountRepository.findById(transferRequest.getToAccountId())
                    .orElseThrow(() -> new IllegalArgumentException("To account not found"));

            // Check if the 'from' account has sufficient balance
            BigDecimal amountToTransfer = BigDecimal.valueOf(transferRequest.getAmount());
            if (fromAccount.getBalance().compareTo(amountToTransfer) < 0) {
                return "Insufficient balance";  // Return message on failure
            }

            // Check for any potential issues before proceeding with the transfer
            if (fromAccount.getBalance().subtract(amountToTransfer).compareTo(BigDecimal.ZERO) < 0) {
                return "Transfer would result in a negative balance in the source account.";
            }

            // Deduct the amount from the 'from' account and add it to the 'to' account
            fromAccount.setBalance(fromAccount.getBalance().subtract(amountToTransfer));
            toAccount.setBalance(toAccount.getBalance().add(amountToTransfer));

            // Save the updated accounts
            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);

            // Create a transaction record
            Transaction transaction = new Transaction();
            transaction.setFromAccount(fromAccount);
            transaction.setToAccount(toAccount);
            transaction.setAmount(amountToTransfer);
            transaction.setTransactionDate(LocalDateTime.now());
            transaction.setStatus("Completed");

            // Save the transaction
            transactionRepository.save(transaction);

            // Log successful transfer
            logger.info("Transfer successful from account ID: {} to account ID: {}",
                    transferRequest.getFromAccountId(), transferRequest.getToAccountId());

            return "Transaction successful";
        } catch (IllegalArgumentException ex) {
            // Log error for known issues
            logger.error("Error during transfer: {}", ex.getMessage());
            return "Error: " + ex.getMessage();
        } catch (Exception ex) {
            // Log unexpected errors
            logger.error("Unexpected error occurred during transfer: ", ex);
            return "Unexpected error occurred";
        }
    }

    @Override
    public List<TransactionDTO> getTransactionsByAccountId(Long accountId, int page, int size) {
        List<Transaction> transactions = transactionRepository.findByAccountId(accountId);
        return transactions.stream()
                .map(TransactionDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        // Ensure the account exists
        Account account = accountRepository.findById(transactionDTO.getAccountId())
                .orElseThrow(() -> new NotFoundException("Account not found with ID: " + transactionDTO.getAccountId()));

        // Create a new transaction
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setTransactionType(transactionDTO.getTransactionType());

        // Set the transaction date (make sure transactionDTO has the correct field)
        transaction.setDate(transactionDTO.getTransactionDate().atStartOfDay());  // Convert LocalDate to LocalDateTime

        // Save the transaction entity to the database
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Return the created transaction as a DTO
        return new TransactionDTO(savedTransaction);
    }

    @Override
    public void deleteTransaction(Long transactionId) {
        if (!transactionRepository.existsById(transactionId)) {
            throw new NotFoundException("Transaction not found");
        }
        transactionRepository.deleteById(transactionId);
    }

    @Override
    public TransactionDTO updateTransaction(Long transactionId, TransactionDTO transactionDTO) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new NotFoundException("Transaction not found with id: " + transactionId));

        transaction.setAmount(transactionDTO.getAmount());
        transaction.setTransactionType(transactionDTO.getTransactionType());
        Transaction updatedTransaction = transactionRepository.save(transaction);

        return new TransactionDTO(updatedTransaction);
    }

    @Override
    public TransactionDTO getTransactionById(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new NotFoundException("Transaction not found with id: " + transactionId));

        return new TransactionDTO(transaction);
    }
}
