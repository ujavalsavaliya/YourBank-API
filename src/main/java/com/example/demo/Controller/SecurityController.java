package com.example.demo.Controller;

import com.example.demo.Entity.SecurityFeature;
import com.example.demo.Services.RedisSecurityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/security")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class SecurityController {

    private final RedisSecurityService service;

    public SecurityController(RedisSecurityService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public String saveFeature(@RequestBody SecurityFeature feature) throws JsonProcessingException {
        service.save(feature);
        return "✅ Saved to Redis";
    }

    @GetMapping
    public List<SecurityFeature> fetchAllFeatures() {
        return service.getAll();
    }
}
