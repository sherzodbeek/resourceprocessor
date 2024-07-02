package com.epam.resourceprocessor.service.impl;

import com.epam.resourceprocessor.feign.ResourceServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ResourceService {

    private final ResourceServiceClient resourceServiceClient;

    public ResourceService(ResourceServiceClient resourceServiceClient) {
        this.resourceServiceClient = resourceServiceClient;
    }

    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 1000, maxDelay = 5000))
    public Resource getFileFromResourceService(Integer id) {
        return resourceServiceClient.getFile(id);
    }

    @Recover
    Resource recover(Exception ex, Integer id) {
        log.error("Exception occurred while retrieving file with {} ID.\nException message: {}", id, ex.getMessage());
        throw new RuntimeException("ResourceService is not working!");
    }
}
