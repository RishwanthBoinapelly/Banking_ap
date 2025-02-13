package com.example.demo.Service;

import com.example.demo.dto.LoanDTO;
import java.util.List;

public interface LoanService {

    // Fetch a loan by ID
    LoanDTO getLoanById(Long id);

    // Fetch all loans for a specific user
    List<LoanDTO> getLoansByUserId(Long userId);

    // Create a new loan
    LoanDTO createLoan(LoanDTO loanDTO);

    // Update a loan
    LoanDTO updateLoan(Long id, LoanDTO loanDTO);

    // Delete a loan
    void deleteLoan(Long id);
}
