package com.epam.resourceprocessor.service.impl;

import com.epam.resourceprocessor.dto.CreateSongMetadataResponse;
import com.epam.resourceprocessor.dto.SongMetadataDto;
import com.epam.resourceprocessor.feign.SongServiceClient;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SongService {

    private final SongServiceClient songServiceClient;

    public SongService(SongServiceClient songServiceClient) {
        this.songServiceClient = songServiceClient;
    }

    @Retryable(retryFor = Exception.class, maxAttempts = 5, backoff = @Backoff(delay = 1000, maxDelay = 5000))
    public CreateSongMetadataResponse sendDataToSongService(SongMetadataDto dto) throws RetryableException {
        return songServiceClient.createSongMetaData(dto);
    }

    @Recover
    CreateSongMetadataResponse recover(Exception ex, SongMetadataDto dto) {
        log.error("Exception occurred while sending {} to SongService.\nException message: {}", dto, ex.getMessage());
        throw new RuntimeException("SongService is not working!");
    }
}
