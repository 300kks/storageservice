package com.vmokrecov.storageservice.services.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.vmokrecov.storageservice.dto.BucketDTO;
import com.vmokrecov.storageservice.services.ObjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class ObjectServiceImpl implements ObjectService {

    private final AmazonS3 amazonS3;

    @Override
    public PutObjectResult uploadObject(String bucketName, FilePart parts) {
        List<ByteBuffer> bytesList = new LinkedList<>();
        parts.content().subscribe(item -> bytesList.add(item.asByteBuffer()));
        int totalBytes = bytesList.stream().mapToInt(Buffer::capacity).sum();
        ByteBuffer buffer = ByteBuffer.allocate(totalBytes);
        bytesList.forEach(buffer::put);

        return amazonS3.putObject(
                bucketName,
                parts.filename(),
                new ByteArrayInputStream(buffer.array()),
                new ObjectMetadata());
    }

    @Override
    public byte[] downloadObject(BucketDTO bucket, String fileName) throws IOException {
        S3Object s3object = amazonS3.getObject(bucket.getName(), fileName);
        S3ObjectInputStream inputStream = s3object.getObjectContent();

        return IOUtils.toByteArray(inputStream);
    }

    @Override
    public ObjectListing listObjects(BucketDTO bucket) {
        return amazonS3.listObjects(bucket.getName());
    }

    @Override
    public Mono<BucketDTO> deleteObject(BucketDTO bucket, String fileName) {
        try {
            amazonS3.deleteObject(bucket.getName(), fileName);
            return Mono.just(bucket);
        } catch (AmazonServiceException e) {
            return Mono.error(e);
        }
    }
}
