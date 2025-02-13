package com.example.demo.Service;

import com.example.demo.dto.AccountDTO;
import java.util.List;

public interface AccountService {

    // Fetch account by ID
    AccountDTO getAccountById(Long id);

    // Fetch all accounts for a user
    List<AccountDTO> getAccountsByUserId(Long userId);

    // Create a new account for user
    AccountDTO createAccount(Long userId, AccountDTO accountDTO);

    // Update account by ID
    AccountDTO updateAccount(Long id, AccountDTO accountDTO);

    // Delete account by ID
    void deleteAccount(Long id);
}
