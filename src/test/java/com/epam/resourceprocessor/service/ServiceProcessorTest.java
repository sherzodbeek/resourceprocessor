package com.epam.resourceprocessor.service;

import com.epam.resourceprocessor.dto.CreateSongMetadataResponse;
import com.epam.resourceprocessor.dto.SongMetadataDto;
import com.epam.resourceprocessor.service.impl.ResourceService;
import com.epam.resourceprocessor.service.impl.ServiceProcessorImpl;
import com.epam.resourceprocessor.service.impl.SongService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceProcessorTest {

    @Mock
    ResourceService resourceService;

    @Mock
    SongService songService;

    @InjectMocks
    ServiceProcessorImpl serviceProcessor;

    @Test
    void processSongMetaData() throws IOException {
        // given
        InputStream inStream = getClass().getClassLoader().getResourceAsStream(
                "file/music.mp3");
        Resource resource = new ByteArrayResource(inStream.readAllBytes());
        when(resourceService.getFileFromResourceService(anyInt())).thenReturn(resource);
        when(songService.sendDataToSongService(any())).thenReturn(CreateSongMetadataResponse.builder().id(1).build());

        // when
        serviceProcessor.processSongMetadata(1);

        // then
        verify(resourceService).getFileFromResourceService(1);
        verify(songService).sendDataToSongService(any());
    }

    @Test
    void getSongMetaData() throws IOException {
        // given
        InputStream inStream = getClass().getClassLoader().getResourceAsStream(
                "file/music.mp3");
        Resource resource = new ByteArrayResource(inStream.readAllBytes());

        // when
        SongMetadataDto result = serviceProcessor.getSongMetaData(1, resource);

        // then
        assertNotNull(result);
        assertEquals(1, result.getResourceId());
    }
}
