package com.epam.resourceprocessor.service;

import com.epam.resourceprocessor.dto.SongMetadataDto;

public interface ServiceProcessor {

    SongMetadataDto getSongMetaData(byte[] file);
}
