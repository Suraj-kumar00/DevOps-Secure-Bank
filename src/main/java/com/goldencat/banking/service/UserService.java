package com.goldencat.banking.service;

import com.goldencat.banking.model.Account;

public interface UserService {
    Account registerUser(String username, String password);
    Account findByUsername(String username);
} 