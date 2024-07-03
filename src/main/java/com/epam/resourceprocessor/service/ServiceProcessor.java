package com.epam.resourceprocessor.service;

import com.epam.resourceprocessor.dto.SongMetadataDto;
import org.springframework.core.io.Resource;

public interface ServiceProcessor {

    SongMetadataDto getSongMetaData(Integer id, Resource resource);

    void processSongMetadata(Integer id);
}
