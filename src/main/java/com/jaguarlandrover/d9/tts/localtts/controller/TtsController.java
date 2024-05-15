package com.jaguarlandrover.d9.tts.localtts.controller;

import com.jaguarlandrover.d9.tts.localtts.model.TtsResponse;
import com.jaguarlandrover.d9.tts.localtts.services.PollyService;
import com.jaguarlandrover.d9.tts.localtts.services.S3Service;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.polly.model.OutputFormat;

@RestController
@Slf4j
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
  public ResponseEntity<TtsResponse> getS3LinkForAudio(@RequestHeader("text") String inputText) {
    log.info("Text to convert: {}", inputText);
    InputStream audioStream = pollyService.synthesize(inputText, OutputFormat.MP3);

    String fileUrl = s3Service.putAudioInS3(audioStream);

    return ResponseEntity.status(200).body(
        TtsResponse.builder().fileUrl(fileUrl).build());
  }
}
