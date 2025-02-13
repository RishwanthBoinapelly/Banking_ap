package com.example.demo.Controller;
// import com.example.demo.entity.Account;
// import com.example.demo.entity.Transaction; // Import the Transaction entity

import com.example.demo.dto.TransactionDTO;
import com.example.demo.Service.TransactionService;
// import com.example.demo.Service.AccountService;
// import com.example.demo.repository.AccountRepository;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.dto.TransferRequestDTO;
import jakarta.validation.Valid;
import java.util.List;
import com.example.demo.exception.NotFoundException;

@RestController
@RequestMapping
public class TransactionController {
    private  final TransactionService transactionService;
    // @Autowired
    // private AccountRepository accountRepository; 
    
    // @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    @PostMapping("transactions/transfer")
    public ResponseEntity<String> transferFunds(@RequestBody TransferRequestDTO transferRequest) {
        try {
            transactionService.transferFunds(transferRequest);
            return ResponseEntity.ok("Transfer Successful");
        } catch (NotFoundException ex) {
            // Handle specific exception
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());}
        }

    // Get transaction details by transaction ID
    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long id) {
        TransactionDTO transactionDTO = transactionService.getTransactionById(id);
        return ResponseEntity.ok(transactionDTO);
    }

    // Get all transactions for a specific account
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByAccountId(
            @PathVariable Long accountId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<TransactionDTO> transactions = transactionService.getTransactionsByAccountId(accountId, page, size);
        return ResponseEntity.ok(transactions);
    }

    // Create a new transaction
    @PostMapping("/transactions/{id}")
    public ResponseEntity<TransactionDTO> createTransaction(@RequestBody TransactionDTO transactionDTO) {
        TransactionDTO createdTransaction = transactionService.createTransaction(transactionDTO);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }
    
        // Save and return the saved entity as DTO
       



    // Update transaction details
    @PutMapping("/{id}")
    public ResponseEntity<TransactionDTO> updateTransaction(@PathVariable Long id, @Valid @RequestBody TransactionDTO transactionDTO) {
        TransactionDTO updatedTransaction = transactionService.updateTransaction(id, transactionDTO);
        return ResponseEntity.ok(updatedTransaction);
    }

    // Delete a transaction by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}
