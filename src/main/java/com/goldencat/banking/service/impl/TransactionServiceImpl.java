package com.goldencat.banking.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goldencat.banking.model.Account;
import com.goldencat.banking.model.Transaction;
import com.goldencat.banking.repository.AccountRepository;
import com.goldencat.banking.repository.TransactionRepository;
import com.goldencat.banking.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    @Transactional
    public void deposit(Account account, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Deposit amount must be positive");
        }

        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setType("DEPOSIT");
        transaction.setDescription("Cash deposit");
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setBalance(account.getBalance());
        transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public void withdraw(Account account, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Withdrawal amount must be positive");
        }

        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount.negate());
        transaction.setType("WITHDRAWAL");
        transaction.setDescription("Cash withdrawal");
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setBalance(account.getBalance());
        transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public void transfer(Account fromAccount, Account toAccount, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Transfer amount must be positive");
        }

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        if (fromAccount.getId().equals(toAccount.getId())) {
            throw new RuntimeException("Cannot transfer to the same account");
        }

        // Withdraw from source account
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        accountRepository.save(fromAccount);

        // Deposit to target account
        toAccount.setBalance(toAccount.getBalance().add(amount));
        accountRepository.save(toAccount);

        // Record transactions
        Transaction withdrawalTx = new Transaction();
        withdrawalTx.setAccount(fromAccount);
        withdrawalTx.setAmount(amount.negate());
        withdrawalTx.setType("TRANSFER_OUT");
        withdrawalTx.setTimestamp(LocalDateTime.now());
        withdrawalTx.setDescription("Transfer to " + toAccount.getUsername());
        withdrawalTx.setBalance(fromAccount.getBalance());
        transactionRepository.save(withdrawalTx);

        Transaction depositTx = new Transaction();
        depositTx.setAccount(toAccount);
        depositTx.setAmount(amount);
        depositTx.setType("TRANSFER_IN");
        depositTx.setTimestamp(LocalDateTime.now());
        depositTx.setDescription("Transfer from " + fromAccount.getUsername());
        depositTx.setBalance(toAccount.getBalance());
        transactionRepository.save(depositTx);
    }

    @Override
    public List<Transaction> getTransactionHistory(Account account) {
        return transactionRepository.findByAccountOrderByTimestampDesc(account);
    }
} 