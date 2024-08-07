package com.epam.resourceprocessor.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SongMetadataDto {

    private String name;
    private String artist;
    private String album;
    private String length;
    private Integer resourceId;
    private Short year;
}
