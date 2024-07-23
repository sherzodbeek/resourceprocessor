package com.epam.resourceprocessor.contract;

import com.epam.resourceprocessor.dto.SongMetadataDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@AutoConfigureStubRunner(
        stubsMode = StubRunnerProperties.StubsMode.LOCAL,
        ids = "com.epam:songservice:+:stubs:8082")
class SongServiceContractTest {

    static {
        System.setProperty("stubrunner.cloud.enabled", "false");
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void send_song_meta_data_to_song_service() {
        SongMetadataDto songMetadataDto = SongMetadataDto.builder()
           .name("Test Name")
           .artist("Test Artist")
           .album("Test Album")
           .length("3:30")
           .year((short) 2024)
           .resourceId(1)
           .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SongMetadataDto> request = new HttpEntity<>(songMetadataDto, headers);

        ResponseEntity<String> result = restTemplate
                .postForEntity("http://localhost:8082/api/songs", request,
                        String.class);

        assertNotNull(result);
    }
}
