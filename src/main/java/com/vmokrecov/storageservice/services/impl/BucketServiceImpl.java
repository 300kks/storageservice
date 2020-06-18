package com.vmokrecov.storageservice.services.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.vmokrecov.storageservice.dto.BucketDTO;
import com.vmokrecov.storageservice.services.BucketService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@AllArgsConstructor
public class BucketServiceImpl implements BucketService {

    private final AmazonS3 amazonS3;

    @Override
    public Bucket createBucket(BucketDTO bucketDTO) {
        if (amazonS3.doesBucketExistV2(bucketDTO.getName())) {
            throw new AmazonS3Exception("Bucket name is not available.");
        }
        return amazonS3.createBucket(bucketDTO.getName());
    }

    @Override
    public List<Bucket> listBuckets() {
        return amazonS3.listBuckets();
    }

    @Override
    public Mono<BucketDTO> deleteBucket(BucketDTO bucket) {
        try {
            amazonS3.deleteBucket(bucket.getName());
            return Mono.just(bucket);
        } catch (AmazonServiceException e) {
            return Mono.error(e);
        }
    }
}
