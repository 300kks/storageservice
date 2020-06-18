package com.vmokrecov.storageservice.services;

import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.vmokrecov.storageservice.dto.BucketDTO;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface ObjectService {

    PutObjectResult uploadObject(String bucketName, FilePart parts);
    byte[] downloadObject(BucketDTO bucket, String fileName) throws IOException;
    ObjectListing listObjects(BucketDTO bucket);
    Mono<BucketDTO> deleteObject(BucketDTO bucket, String fileName);
}
