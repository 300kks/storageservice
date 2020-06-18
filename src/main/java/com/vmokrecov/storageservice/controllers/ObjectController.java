package com.vmokrecov.storageservice.controllers;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/object")
@AllArgsConstructor
@Api(tags = "ObjectController admin", description = "ObjectController description")
public class ObjectController {

}
