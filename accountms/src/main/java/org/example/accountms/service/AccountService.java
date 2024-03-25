package org.example.accountms.service;

import org.example.accountms.model.Account;

import java.util.List;

public interface AccountService {
    Account saveAccount(Account account);

    Account getAccountById(String accountNumber);

    List<Account> getAllAccounts();

    void deleteAccountById(String accountNumber);
}
