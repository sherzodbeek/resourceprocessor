package com.epam.resourceprocessor.feign;

import com.epam.resourceprocessor.dto.CreateSongMetadataResponse;
import com.epam.resourceprocessor.dto.SongMetadataDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "${song.service.name}", url = "${song.service.url}")
public interface SongServiceClient {

    @GetMapping("/{id}")
    SongMetadataDto getSongMetadata(@PathVariable Integer id);

    @PostMapping
    CreateSongMetadataResponse createSongMetaData(@RequestBody SongMetadataDto dto);
}
