package com.example.demo.Services;

import com.example.demo.Entity.DepositEntity;
import com.example.demo.Entity.RegisterEntity;
import com.example.demo.Repositry.DepositRepo;
import com.example.demo.Repositry.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class RegisterService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private DepositRepo depositRepo;

    public ResponseEntity<?> create_user(RegisterEntity registerEntity) {
        RegisterEntity userEmail = userRepo.findByEmail(registerEntity.getEmail());
        RegisterEntity userUsername = userRepo.findByUsername(registerEntity.getUsername());

        if (userEmail != null) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        if (userUsername != null) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        registerEntity.assignAcc(registerEntity.getUsername() + generateRandomAccNo());
        registerEntity.setPassword(passwordEncoder.encode(registerEntity.getPassword()));
        registerEntity.assignBal(5000);
        DepositEntity deposit = new DepositEntity();
        deposit.setAmount((long)5000);
        deposit.setDepositType("First Deposit");
        deposit.setUsername(registerEntity.getUsername());
        depositRepo.save(deposit);

        userRepo.save(registerEntity);
        return ResponseEntity.ok().body("User Saved");
    }

    public int generateRandomAccNo() {
        Random random = new Random();
        int min = 100000;
        int max = 999999;
        return random.nextInt(max - min + 1) + min;
    }
}
