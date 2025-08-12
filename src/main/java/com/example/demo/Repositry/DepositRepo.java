package com.example.demo.Repositry;

import com.example.demo.Entity.DepositEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DepositRepo extends MongoRepository<DepositEntity, ObjectId> {
    public List<DepositEntity> findByUsername(String username);
}
