package org.example.accountms.service;

import org.example.accountms.model.Account;
import org.example.accountms.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account getAccountById(String accountNumber) {
        Optional<Account> optionalAccount = accountRepository.findById(accountNumber);
        return optionalAccount.orElse(null);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public void deleteAccountById(String accountNumber) {
        accountRepository.deleteById(accountNumber);
    }
}
