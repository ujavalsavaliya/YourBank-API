package com.example.demo.Repositry;

import com.example.demo.Entity.DepositEntity;
import com.example.demo.Entity.WithdrawEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WithdrawRepo extends MongoRepository<WithdrawEntity, ObjectId> {
    public List<WithdrawEntity> findByUsername(String username);
}
