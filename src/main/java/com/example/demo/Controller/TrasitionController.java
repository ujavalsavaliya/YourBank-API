package com.example.demo.Controller;

import com.example.demo.Services.TrasitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TrasitionController {

    @Autowired
    private TrasitionService trasitionService;

    @GetMapping("/all-deposit/{username}")
    public ResponseEntity<?> getDeposit(@PathVariable("username") String Username) {
        return trasitionService.getDeposit(Username);
    }

    @GetMapping("/all-withdraw/{username}")
    public ResponseEntity<?> getWithdraw(@PathVariable("username") String Username)
    {
        return trasitionService.getWithdraw(Username);
    }

    @GetMapping("/all-transfer/{username}")
    public ResponseEntity<?> getTransfer(@PathVariable("username") String Username)
    {
        return trasitionService.getTransfer(Username);
    }

    @GetMapping("all-trasition/{username}")
    public ResponseEntity<?> getTrasition(@PathVariable("username") String Username){
        return trasitionService.getTrasition(Username);
    }
}
