package com.example.demo.Service;

import com.example.demo.dto.TransactionDTO;
import com.example.demo.dto.TransferRequestDTO;

import java.util.List;

public interface TransactionService {

    // Get transactions for a specific account with pagination
    List<TransactionDTO> getTransactionsByAccountId(Long accountId, int page, int size);

    // Create a new transaction
    TransactionDTO createTransaction(TransactionDTO transactionDTO);

    // Delete a transaction by ID
    void deleteTransaction(Long transactionId);

    // Update a transaction
    TransactionDTO updateTransaction(Long transactionId, TransactionDTO transactionDTO);

    // Get transaction details by ID
    TransactionDTO getTransactionById(Long transactionId);
    String transferFunds(TransferRequestDTO transferRequest);
}
