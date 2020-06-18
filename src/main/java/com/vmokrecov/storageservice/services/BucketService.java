package com.vmokrecov.storageservice.services;

import com.amazonaws.services.s3.model.Bucket;
import com.vmokrecov.storageservice.dto.BucketDTO;
import reactor.core.publisher.Mono;

import java.util.List;

public interface BucketService {

    Bucket createBucket(BucketDTO bucketDTO);
    List<Bucket> listBuckets();
    Mono<BucketDTO> deleteBucket(BucketDTO bucket);
}
