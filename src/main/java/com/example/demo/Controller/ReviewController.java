package com.example.demo.Controller;

import com.example.demo.Entity.ReviewEntity;
import com.example.demo.Repositry.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewRepo reviewRepo;

    @GetMapping
    public List<ReviewEntity> getAllReview()
    {
        return reviewRepo.findAll();
    }

    @PostMapping
    public ReviewEntity createReview(@RequestBody ReviewEntity reviewEntity) {
        ReviewEntity saved = reviewRepo.save(reviewEntity);
        return saved;
    }

}
