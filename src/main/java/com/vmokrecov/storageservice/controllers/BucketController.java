package com.vmokrecov.storageservice.controllers;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bucket")
@AllArgsConstructor
@Api(tags = "BucketController admin", description = "BucketController description")
public class BucketController {

}
