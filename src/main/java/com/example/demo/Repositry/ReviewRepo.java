package com.example.demo.Repositry;

import com.example.demo.Entity.ReviewEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReviewRepo extends MongoRepository<ReviewEntity,String> {
}
