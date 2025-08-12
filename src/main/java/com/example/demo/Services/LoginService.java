package com.example.demo.Services;

import com.example.demo.Config.jwtTokenCreation;
import com.example.demo.Entity.LoginEntity;
import com.example.demo.Entity.RegisterEntity;
import com.example.demo.Repositry.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private jwtTokenCreation jwtTokenCreation;

    public ResponseEntity<?> login(LoginEntity login)
    {
        RegisterEntity user = userRepo.findByUsername(login.getUsername());
        if(user != null)
        {
            if(passwordEncoder.matches(login.getPassword(),user.getPassword()))
            {
                String token = jwtTokenCreation.makeToken(user.getUsername());
                return ResponseEntity.ok(token);
            }
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Retry login");
    }
}
