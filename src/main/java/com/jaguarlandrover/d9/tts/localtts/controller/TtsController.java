package com.jaguarlandrover.d9.tts.localtts.controller;

import com.jaguarlandrover.d9.tts.localtts.model.TtsResponse;
import com.jaguarlandrover.d9.tts.localtts.services.PollyService;
import com.jaguarlandrover.d9.tts.localtts.services.S3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import software.amazon.awssdk.services.polly.model.OutputFormat;

@RestController
@RequestMapping(value = "/api")
public class TtsController {

    S3Service s3Service;

    PollyService pollyService;

    //@Autowired
    public TtsController(S3Service s3Service, PollyService pollyService) {
        this.s3Service = s3Service;
        this.pollyService = pollyService;
    }

    @GetMapping(path = "/polly/")
    public ResponseEntity<TtsResponse> getS3LinkForAudio(){

        InputStream audioStream = null;
       // try {
            audioStream = pollyService.synthesize("You won't get to Tesco with this level of battery", OutputFormat.MP3);
        /*} catch (IOException e) {
            //TODO create a proper way to handle exceptions
            e.printStackTrace();
        }*/

        String fileUrl = s3Service.putAudioInS3(audioStream);

        return ResponseEntity.status(200).body(TtsResponse.builder().fileUrl(fileUrl).build());
    }
}
