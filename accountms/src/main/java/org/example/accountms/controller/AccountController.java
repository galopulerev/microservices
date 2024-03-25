package org.example.accountms.controller;

import org.example.accountms.messaging.TransactionMessageProducer;
import org.example.accountms.model.Account;
import org.example.accountms.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
public class AccountController {

    private final AccountService accountService;
    private final TransactionMessageProducer transactionMessageProducer;

    public AccountController(AccountService accountService, TransactionMessageProducer transactionMessageProducer) {
        this.accountService = accountService;
        this.transactionMessageProducer = transactionMessageProducer;
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account savedAccount = accountService.saveAccount(account);
        transactionMessageProducer.sendMessage(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAccount);
    }

    @PutMapping("/{accountNumber}")
    public ResponseEntity<?> updateAccount(@PathVariable String accountNumber, @RequestBody Account accountDetails) {
        Account existingAccount = accountService.getAccountById(accountNumber);
        if (existingAccount != null) {
            existingAccount.setAccountType(accountDetails.getAccountType());
            existingAccount.setOpeningBalance(accountDetails.getOpeningBalance());
            existingAccount.setStatus(accountDetails.getStatus());
            existingAccount.setClientId(accountDetails.getClientId());

            Account updatedAccount = accountService.saveAccount(existingAccount);
            return ResponseEntity.ok(updatedAccount);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cuenta no encontrada con el número de cuenta: " + accountNumber);
        }
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<?> getAccountById(@PathVariable String accountNumber) {
        Account account = accountService.getAccountById(accountNumber);
        if (account != null) {
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cuenta no encontrada con el número de cuenta: " + accountNumber);
        }
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<Void> deleteAccount(@PathVariable String accountNumber) {
        accountService.deleteAccountById(accountNumber);
        return ResponseEntity.noContent().build();
    }

    @PutMapping()
    public ResponseEntity<?> updateClientName(@RequestParam String accountNumber, @RequestParam String clientName) {
        Account existingAccount = accountService.getAccountById(accountNumber);
        if (existingAccount != null) {
            existingAccount.setClientName(clientName);
            Account updatedAccount = accountService.saveAccount(existingAccount);
            return ResponseEntity.ok(updatedAccount);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cuenta no encontrada con el número de cuenta: " + accountNumber);
        }
    }
}
