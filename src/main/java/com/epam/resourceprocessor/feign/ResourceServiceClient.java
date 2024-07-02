package com.epam.resourceprocessor.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "${resource.service.name}", url = "${resource.service.url}")
public interface ResourceServiceClient {

    @GetMapping("/{id}")
    Resource getFile(@PathVariable Integer id);
}
