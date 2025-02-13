package com.example.demo.entity;

public enum TransactionStatus {
    PENDING,    // Transaction is pending
    COMPLETED,  // Transaction is successfully completed
    FAILED,     // Transaction has failed
    CANCELLED,  // Transaction was canceled
    REVERSED    // Transaction was reversed
}
