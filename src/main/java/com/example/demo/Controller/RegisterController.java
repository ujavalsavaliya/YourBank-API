package com.example.demo.Controller;

import com.example.demo.Entity.LoginEntity;
import com.example.demo.Entity.RegisterEntity;
import com.example.demo.Services.LoginService;
import com.example.demo.Services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

}
