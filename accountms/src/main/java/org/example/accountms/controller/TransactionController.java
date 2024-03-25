package org.example.accountms.controller;

import org.example.accountms.messaging.TransactionMessageProducer;
import org.example.accountms.model.Transaction;
import org.example.accountms.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        Transaction savedTransaction = transactionService.saveTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTransaction);
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long transactionId, @RequestBody Transaction transactionDetails) {
        Transaction existingTransaction = transactionService.getTransactionById(transactionId);
        if (existingTransaction != null) {
            existingTransaction.setAccountNumber(transactionDetails.getAccountNumber());
            existingTransaction.setCreatedOn(transactionDetails.getCreatedOn());
            existingTransaction.setAmount(transactionDetails.getAmount());
            existingTransaction.setTransactionType(transactionDetails.getTransactionType());
            existingTransaction.setBalance(transactionDetails.getBalance());

            Transaction updatedTransaction = transactionService.saveTransaction(existingTransaction);
            return ResponseEntity.ok(updatedTransaction);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long transactionId) {
        Transaction transaction = transactionService.getTransactionById(transactionId);
        if (transaction != null) {
            return ResponseEntity.ok(transaction);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long transactionId) {
        transactionService.deleteTransactionById(transactionId);
        return ResponseEntity.noContent().build();
    }

}
