package org.example.accountms.service;

import org.example.accountms.model.Transaction;
import org.example.accountms.dto.TransactionReportDTO;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    Transaction saveTransaction(Transaction transaction);

    Transaction getTransactionById(Long transactionId);

    List<Transaction> getAllTransactions();

    void deleteTransactionById(Long transactionId);

    List<TransactionReportDTO> getTransactionsByClientIdAndDateRange(Long clientId, LocalDate startDate, LocalDate endDate);
}
