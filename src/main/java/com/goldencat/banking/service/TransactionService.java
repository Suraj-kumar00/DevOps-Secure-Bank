package com.goldencat.banking.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.goldencat.banking.model.Account;
import com.goldencat.banking.model.Transaction;

@Service
public interface TransactionService {
    void deposit(Account account, BigDecimal amount);
    void withdraw(Account account, BigDecimal amount);
    void transfer(Account fromAccount, Account toAccount, BigDecimal amount);
    List<Transaction> getTransactionHistory(Account account);
} 