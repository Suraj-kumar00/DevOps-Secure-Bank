package com.goldencat.banking.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.goldencat.banking.model.Account;
import com.goldencat.banking.service.TransactionService;
import com.goldencat.banking.service.UserService;

@Controller
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @PostMapping("/deposit")
    public String deposit(@RequestParam BigDecimal amount,
                         Authentication authentication,
                         RedirectAttributes redirectAttributes) {
        try {
            Account account = userService.findByUsername(authentication.getName());
            transactionService.deposit(account, amount);
            redirectAttributes.addFlashAttribute("success", "Successfully deposited $" + amount);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/dashboard";
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam BigDecimal amount,
                          Authentication authentication,
                          RedirectAttributes redirectAttributes) {
        try {
            Account account = userService.findByUsername(authentication.getName());
            transactionService.withdraw(account, amount);
            redirectAttributes.addFlashAttribute("success", "Successfully withdrawn $" + amount);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/dashboard";
    }

    @PostMapping("/transfer")
    public String transfer(@RequestParam String toUsername,
                         @RequestParam BigDecimal amount,
                         Authentication authentication,
                         RedirectAttributes redirectAttributes) {
        try {
            Account fromAccount = userService.findByUsername(authentication.getName());
            Account toAccount = userService.findByUsername(toUsername);
            transactionService.transfer(fromAccount, toAccount, amount);
            redirectAttributes.addFlashAttribute("success", 
                "Successfully transferred $" + amount + " to " + toUsername);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/dashboard";
    }

    @GetMapping("/transactions")
    public String viewTransactions(Authentication authentication, Model model) {
        Account account = userService.findByUsername(authentication.getName());
        model.addAttribute("transactions", transactionService.getTransactionHistory(account));
        return "transactions";
    }
} 