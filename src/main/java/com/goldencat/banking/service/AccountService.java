package com.goldencat.banking.service;

import org.springframework.stereotype.Service;

import com.goldencat.banking.model.Account;

@Service
public interface AccountService {
    Account findByUsername(String username);
} 