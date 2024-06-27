package com.epam.resourceprocessor.service.impl;

import com.epam.resourceprocessor.dto.SongMetadataDto;
import com.epam.resourceprocessor.exception.MetadataParserException;
import com.epam.resourceprocessor.service.ServiceProcessor;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.springframework.stereotype.Service;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ServiceProcessorImpl implements ServiceProcessor {

    @Override
    public SongMetadataDto getSongMetaData(byte[] file) {
        try (InputStream input = new ByteArrayInputStream(file)) {
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
                    .year(metadata.get("xmpDM:releaseDate"))
                    .build();
        } catch (IOException | TikaException | SAXException e) {
            throw new MetadataParserException("Exception occurred while parsing file metadata", e);
        }
    }
}
