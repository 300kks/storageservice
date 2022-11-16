package com.vmokrecov.storageservice.controllers;

import com.amazonaws.services.s3.model.Bucket;
import com.vmokrecov.storageservice.dto.BucketDTO;
import com.vmokrecov.storageservice.services.BucketService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/bucket")
@AllArgsConstructor
@Tag(name = "BucketController admin", description = "BucketController description")
public class BucketController {

    private final BucketService bucketService;

    @GetMapping("/")
    public Mono<String> home() {
        return Mono.just("{ \"message\": \"Home\" }");
    }

    @PostMapping("/create")
    public Mono<Bucket> createBucket(@RequestBody BucketDTO bucketDTO) {
        return Mono.just(bucketService.createBucket(bucketDTO));
    }

    @GetMapping("/list")
    public Flux<List<Bucket>> listBuckets() {
        return Flux.just(bucketService.listBuckets());
    }

    @DeleteMapping("/delete")
    public Mono<BucketDTO> deleteBucket(@RequestBody BucketDTO bucket) {
        return bucketService.deleteBucket(bucket);
    }
}
