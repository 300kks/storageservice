package com.vmokrecov.storageservice.controllers;

import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.vmokrecov.storageservice.dto.BucketDTO;
import com.vmokrecov.storageservice.services.ObjectService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
@RequestMapping("/object")
@AllArgsConstructor
@Api(tags = "ObjectController admin", description = "ObjectController description")
public class ObjectController {

    private final ObjectService objectService;

    @GetMapping("/")
    public Mono<String> home() {
        return Mono.just("{ \"message\": \"Home\" }");
    }

    @PostMapping(value = "/upload/{bucketName}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<PutObjectResult> uploadObject(@PathVariable String bucketName,
                                              @RequestPart("files") FilePart parts) {
        return Mono.just(objectService.uploadObject(bucketName, parts));
    }

    @GetMapping("/download/{fileName}")
    public Mono<ResponseEntity<byte[]>> downloadObject(@RequestBody BucketDTO bucket,
                                                       @PathVariable String fileName) throws IOException {

        byte[] bytes = objectService.downloadObject(bucket, fileName);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentLength(bytes.length);
        httpHeaders.setContentDispositionFormData("attachment", fileName);

        return Mono.just(new ResponseEntity<byte[]>(bytes, httpHeaders, HttpStatus.OK));
    }

    @GetMapping("/list")
    public Flux<ObjectListing> listObjects(@RequestBody BucketDTO bucket) {
        return Flux.just(objectService.listObjects(bucket));
    }

    @DeleteMapping("/{fileName}")
    public Mono<BucketDTO> deleteObject(@RequestBody BucketDTO bucket, @PathVariable String fileName) {
        return objectService.deleteObject(bucket, fileName);
    }
}
