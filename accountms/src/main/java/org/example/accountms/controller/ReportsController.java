package org.example.accountms.controller;

import org.example.accountms.dto.TransactionReportDTO;
import org.example.accountms.service.TransactionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reportes")
public class ReportsController {

    private final TransactionService transactionService;

    public ReportsController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping()
    public List<TransactionReportDTO> getTransactionReport(
            @RequestParam Long clientId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return transactionService.getTransactionsByClientIdAndDateRange(clientId, startDate, endDate);
    }
}
