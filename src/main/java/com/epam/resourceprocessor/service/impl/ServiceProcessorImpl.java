package com.epam.resourceprocessor.service.impl;

import com.epam.resourceprocessor.dto.CreateSongMetadataResponse;
import com.epam.resourceprocessor.dto.SongMetadataDto;
import com.epam.resourceprocessor.exception.MetadataParserException;
import com.epam.resourceprocessor.service.ServiceProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class ServiceProcessorImpl implements ServiceProcessor {

    private final ResourceService resourceService;
    private final SongService songService;

    public ServiceProcessorImpl(ResourceService resourceService, SongService songService) {
        this.resourceService = resourceService;
        this.songService = songService;
    }

    @Override
    @RabbitListener(queues = "${resource.processor.queue}")
    public void processSongMetadata(Integer id) {
        Resource resource = resourceService.getFileFromResourceService(id);
        SongMetadataDto songMetaData = getSongMetaData(id, resource);
        CreateSongMetadataResponse createSongMetadataResponse = songService.sendDataToSongService(songMetaData);
        log.info("Song metadata with {} id created", createSongMetadataResponse.getId());
    }

    @Override
    public SongMetadataDto getSongMetaData(Integer id, Resource resource) {
        try (InputStream input = resource.getInputStream()) {
            ContentHandler handler = new DefaultHandler();
            Metadata metadata = new Metadata();
            ParseContext parseCtx = new ParseContext();
            Parser parser = new Mp3Parser();
            parser.parse(input, handler, metadata, parseCtx);

            String duration = metadata.get("xmpDM:duration");
            if (duration != null) {
                int d = (int) Double.parseDouble(duration);
                duration = (d / 60) + ":" + (d % 60);
            }

            return SongMetadataDto.builder()
                    .name(metadata.get("dc:title"))
                    .artist(metadata.get("xmpDM:artist"))
                    .album(metadata.get("xmpDM:album"))
                    .length(duration)
                    .resourceId(id)
                    .year(Short.valueOf(metadata.get("xmpDM:releaseDate")))
                    .build();
        } catch (IOException | TikaException | SAXException e) {
            throw new MetadataParserException("Exception occurred while parsing file metadata", e);
        }
    }
}
