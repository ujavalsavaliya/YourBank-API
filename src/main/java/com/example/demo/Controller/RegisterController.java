package com.example.demo.Controller;

import com.example.demo.Entity.LoginEntity;
import com.example.demo.Entity.RegisterEntity;
import com.example.demo.Services.LoginService;
import com.example.demo.Services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @Autowired
    private LoginService loginService;

    @PostMapping("/signup")
    public ResponseEntity<?> create_user(@RequestBody RegisterEntity registerEntity){
        return registerService.create_user(registerEntity);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login_user(@RequestBody LoginEntity loginEntity){
        return loginService.login(loginEntity);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<?> dashboard(){
        return ResponseEntity.ok().body("You are right");
    }

    @PostMapping("/verifyOtp/{username}/{otp}")
    public ResponseEntity<?> verifyOtp(@PathVariable String username,@PathVariable String otp){
        return loginService.verifyOtp(username,otp);
    }

    @GetMapping("/resend/{username}")
    public ResponseEntity<?> resendOtp(@PathVariable String username){
        return loginService.ResendOtp(username);
    }
}
