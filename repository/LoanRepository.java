package com.example.demo.repository;

import com.example.demo.entity.Loan;

// import jakarta.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface LoanRepository extends JpaRepository<Loan, Long> {
    // Standard method to delete by ID, already provided by JpaRepository
    // @NotNull
    
    List<Loan> findByUserId(Long userId);
}
