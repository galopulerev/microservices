package org.example.accountms.repository;

import org.example.accountms.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findTopByAccountNumberOrderByCreatedOnDesc(String accountNumber);

    @Query("SELECT t FROM Transaction t JOIN t.account a WHERE a.clientId = :clientId " +
            "AND t.createdOn BETWEEN :startDate AND :endDate")
    List<Transaction> findByClientIdAndDateRange(Long clientId, LocalDateTime startDate, LocalDateTime endDate);

}
