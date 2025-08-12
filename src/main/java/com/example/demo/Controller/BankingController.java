package com.example.demo.Controller;

import com.example.demo.Entity.DepositEntity;
import com.example.demo.Entity.TransferEntity;
import com.example.demo.Entity.WithdrawEntity;
import com.example.demo.Services.BankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")

public class BankingController {

    @Autowired
    private BankingService bankingService;

    @GetMapping("/balance/{username}")
    public ResponseEntity<Long> getBalance(@PathVariable String username) {
        return bankingService.Balance(username);
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody DepositEntity depositEntity){
        return bankingService.deposit(depositEntity);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody WithdrawEntity withdrawEntity){
        return bankingService.withdraw(withdrawEntity);
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransferEntity transfer){
        return bankingService.transfer(transfer);
    }
}
