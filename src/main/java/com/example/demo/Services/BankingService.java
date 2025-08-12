package com.example.demo.Services;

import com.example.demo.Entity.DepositEntity;
import com.example.demo.Entity.RegisterEntity;
import com.example.demo.Entity.TransferEntity;
import com.example.demo.Entity.WithdrawEntity;
import com.example.demo.Config.jwtTokenCreation;
import com.example.demo.Repositry.DepositRepo;
import com.example.demo.Repositry.TransferRepo;
import com.example.demo.Repositry.UserRepo;
import com.example.demo.Repositry.WithdrawRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class BankingService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private DepositRepo depositRepo;

    @Autowired
    private WithdrawRepo withdrawRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TransferRepo transferRepo;

    @Autowired
    private jwtTokenCreation jwtTokenCreation;

    public ResponseEntity<Long> Balance(String username){
        RegisterEntity registerEntity = userRepo.findByUsername(username);
        return ResponseEntity.ok().body(registerEntity.getBalance());
    }

    public ResponseEntity<?> deposit(DepositEntity depositEntity){
        RegisterEntity registerEntity = userRepo.findByUsername(depositEntity.getUsername());
        registerEntity.assignBal(registerEntity.getBalance() + depositEntity.getAmount());
        userRepo.save(registerEntity);
        depositEntity.assignDate(LocalDateTime.now());
        depositRepo.save(depositEntity);
        return ResponseEntity.ok().body("Deposit Suceessfully");
    }

    public ResponseEntity<?> withdraw(WithdrawEntity withdrawEntity){
        RegisterEntity registerEntity = userRepo.findByUsername(withdrawEntity.getUsername());
        if(passwordEncoder.matches(withdrawEntity.getPassword(),registerEntity.getPassword())){
            if(registerEntity.getBalance() > withdrawEntity.getAmount()){
                registerEntity.assignBal(registerEntity.getBalance() - withdrawEntity.getAmount());
                userRepo.save(registerEntity);
                withdrawEntity.assignDate(LocalDateTime.now());
                withdrawRepo.save(withdrawEntity);
                return ResponseEntity.ok().body("Withdraw Suceessfully");
            }
        }
        return ResponseEntity.badRequest().body("OOPs Failed ...");
    }

    public ResponseEntity<?> transfer(TransferEntity transferEntity) {
        RegisterEntity registerEntity = userRepo.findByUsername(transferEntity.getUsername());
        if (registerEntity == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
        }
        if (!passwordEncoder.matches(transferEntity.getPassword(), registerEntity.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
        }

        // Check if balance is sufficient
        if (registerEntity.getBalance() < transferEntity.getAmount()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient balance");
        }
        registerEntity.assignBal(registerEntity.getBalance() - transferEntity.getAmount());
        userRepo.save(registerEntity);
        transferEntity.assignDate(LocalDateTime.now());
        transferRepo.save(transferEntity);
        return ResponseEntity.ok().body("Transfer successfully");
    }

}
