package com.epam.resourceprocessor.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "${resource.service.name}", url = "${gateway.url}", path = "/api/resources")
public interface ResourceServiceClient {

    @GetMapping("/{id}")
    Resource getFile(@PathVariable Integer id);
}
