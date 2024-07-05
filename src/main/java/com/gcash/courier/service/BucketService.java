package com.gcash.courier.service;

import io.github.bucket4j.Bucket;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class BucketService {
    private final Map<String, Bucket> buckets = new HashMap<>();

    public Bucket getBucket(String bucketName) {
        return buckets.computeIfAbsent(bucketName, createNewBucket());
    }

    private Function<String, Bucket> createNewBucket() {
        return name -> Bucket.builder()
                .addLimit(limit -> limit.capacity(20).refillIntervally(20, Duration.ofHours(1)))
                .build();
    }
}
