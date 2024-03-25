package org.example.clientems.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ACCOUNT-SERVICE", url = "${account-service.url}")
public interface AccountClient {

    @PutMapping("/api/cuentas")
    ResponseEntity<?> updateClientName(@RequestParam String accountNumber, @RequestParam String clientName);

}
