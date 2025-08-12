package com.example.demo.Services;

import com.example.demo.Entity.SecurityFeature;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class RedisSecurityService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String KEY_PREFIX = "SecurityFeature:";

    private AtomicLong idCounter = new AtomicLong(1); // for assigning unique keys

    public void save(SecurityFeature feature) throws JsonProcessingException {
        String key = KEY_PREFIX + idCounter.getAndIncrement();
        redisTemplate.opsForValue().set(key, feature);
    }

    public List<SecurityFeature> getAll() {
        Set<String> keys = redisTemplate.keys(KEY_PREFIX + "*");
        if (keys == null || keys.isEmpty()) return Collections.emptyList();

        List<Object> objects = redisTemplate.opsForValue().multiGet(keys);
        List<SecurityFeature> features = new ArrayList<>();
        for (Object obj : objects) {
            if (obj instanceof SecurityFeature) {
                features.add((SecurityFeature) obj);
            }
        }
        return features;
    }
}
