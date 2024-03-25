package org.example.accountms.service;

import org.example.accountms.exception.InsufficientBalanceException;
import org.example.accountms.messaging.TransactionMessageProducer;
import org.example.accountms.model.Account;
import org.example.accountms.model.Transaction;
import org.example.accountms.dto.TransactionReportDTO;
import org.example.accountms.model.TransactionType;
import org.example.accountms.repository.AccountRepository;
import org.example.accountms.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    private final AccountRepository accountRepository;
    private final TransactionMessageProducer transactionMessageProducer;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository, TransactionMessageProducer transactionMessageProducer) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.transactionMessageProducer = transactionMessageProducer;
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {

        if (transaction.getTransactionId() != null) {
            // Edition
            if (transaction.getBalance().add(transaction.getAmount()).compareTo(BigDecimal.ZERO) < 0) {
                throw new InsufficientBalanceException();
            }
            // Set balance
            transaction.setBalance(transaction.getBalance().add(transaction.getAmount()));
        } else {
            // Creation
            Optional<Transaction> lastTransaction = transactionRepository.findTopByAccountNumberOrderByCreatedOnDesc(transaction.getAccountNumber());
            if (lastTransaction.isPresent()) {
                if (lastTransaction.get().getBalance().add(transaction.getAmount()).compareTo(BigDecimal.ZERO) < 0) {
                    throw new InsufficientBalanceException();
                }
                // Set balance
                transaction.setBalance(lastTransaction.get().getBalance().add(transaction.getAmount()));
            } else {
                Account account = accountRepository.findById(transaction.getAccountNumber())
                        .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
                if (account.getOpeningBalance().add(transaction.getAmount()).compareTo(BigDecimal.ZERO) < 0) {
                    throw new InsufficientBalanceException();
                }
                // Set balance
                transaction.setBalance(account.getOpeningBalance().add(transaction.getAmount()));
            }

        }
        transaction.setTransactionType(transaction.getAmount().compareTo(BigDecimal.ZERO) < 0 ? TransactionType.Retiro : TransactionType.Deposito);
        transaction.setCreatedOn(LocalDateTime.now());

        // Set account object to send message to the queue
        /*Account account = accountRepository.findById(transaction.getAccountNumber())
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
        transaction.setAccount(account);
        transactionMessageProducer.sendMessage(transaction);*/

        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction getTransactionById(Long transactionId) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
        return optionalTransaction.orElse(null);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public void deleteTransactionById(Long transactionId) {
        transactionRepository.deleteById(transactionId);
    }

    @Override
    public List<TransactionReportDTO> getTransactionsByClientIdAndDateRange(Long clientId, LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
        List<Transaction> transactions = transactionRepository.findByClientIdAndDateRange(clientId, startDateTime, endDateTime);

        return transactions.stream()
                .map(transaction -> {
                    TransactionReportDTO dto = new TransactionReportDTO();
                    dto.setCreatedOn(LocalDate.from(transaction.getCreatedOn()));
                    dto.setClientName(transaction.getAccount().getClientName());
                    dto.setAccountNumber(transaction.getAccountNumber());
                    dto.setAccountType(transaction.getAccount().getAccountType());
                    dto.setOpeningBalance(transaction.getAccount().getOpeningBalance());
                    dto.setAccountStatus(transaction.getAccount().getStatus());
                    dto.setAmount(transaction.getAmount());
                    dto.setBalance(transaction.getBalance());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
