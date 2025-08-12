package com.example.demo.Repositry;

import com.example.demo.Entity.DepositEntity;
import com.example.demo.Entity.TransferEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TransferRepo extends MongoRepository<TransferEntity, ObjectId> {
    public List<TransferEntity> findByUsername(String username);
}
