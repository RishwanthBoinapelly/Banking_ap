package com.example.demo.Service.impl;

import com.example.demo.Service.LoanService;
import com.example.demo.dto.LoanDTO;
import com.example.demo.entity.Account;
import com.example.demo.entity.Loan;
import com.example.demo.entity.LoanStatus;
import com.example.demo.entity.User;
import com.example.demo.repository.LoanRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.repository.AccountRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    public LoanServiceImpl(LoanRepository loanRepository, UserRepository userRepository,AccountRepository accountRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.accountRepository=accountRepository;
    }

    @Override
    public LoanDTO getLoanById(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found with id: " + id));
        return new LoanDTO(loan);
    }

    @Override
    public List<LoanDTO> getLoansByUserId(Long userId) {
        List<Loan> loans = loanRepository.findByUserId(userId);
        return loans.stream().map(LoanDTO::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LoanDTO createLoan(LoanDTO loanDTO) {
        User user = userRepository.findById(loanDTO.getUserId()) 
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + loanDTO.getUserId()));
                if (loanDTO.getLoanType() == null) {
                    throw new IllegalArgumentException("Loan type must not be null");
                }
        Account account = accountRepository.findById(loanDTO.getAccountId())
            .orElseThrow(() -> new IllegalArgumentException("Account not found with id: " + loanDTO.getAccountId()));

        Loan loan = new Loan();
        loan.setUser(user); 
        loan.setAccount(account); // Setting the User object, not just the userId
        loan.setAmount(loanDTO.getAmount());
        loan.setInterestRate(loanDTO.getInterestRate());
        loan.setLoanStatus(LoanStatus.PENDING); // Set initial status to PENDING
        loan.setIssueDate(loanDTO.getIssueDate());
        loan.setLoanType(loanDTO.getLoanType());

        Loan savedLoan = loanRepository.save(loan);
        return new LoanDTO(savedLoan);
    }

    @Override
    @Transactional
    public LoanDTO updateLoan(Long id, LoanDTO loanDTO) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found with id: " + id));

        loan.setAmount(loanDTO.getAmount());
        loan.setInterestRate(loanDTO.getInterestRate());
        loan.setLoanStatus(loanDTO.getLoanStatus());
        loan.setIssueDate(loanDTO.getIssueDate());

        Loan updatedLoan = loanRepository.save(loan);
        return new LoanDTO(updatedLoan);
    }

    @Override
    @Transactional
    public void deleteLoan(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found with id: " + id));

        loanRepository.delete(loan);  // Deleting the loan if it exists
    }
}
