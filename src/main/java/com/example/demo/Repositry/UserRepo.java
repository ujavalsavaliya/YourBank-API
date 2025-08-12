package com.example.demo.Repositry;

import com.example.demo.Entity.RegisterEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<RegisterEntity,String> {
    public RegisterEntity findByEmail(String email);
    public RegisterEntity findByUsername(String username);
}
