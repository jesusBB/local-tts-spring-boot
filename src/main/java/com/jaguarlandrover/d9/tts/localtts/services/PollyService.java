package com.jaguarlandrover.d9.tts.localtts.services;

import com.jaguarlandrover.d9.tts.localtts.configuration.PollyConfiguration;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.polly.model.OutputFormat;
import software.amazon.awssdk.services.polly.model.SynthesizeSpeechRequest;

@Slf4j
@Service
public class PollyService {
  private PollyConfiguration pollyConfiguration;

  @Autowired
  public PollyService(PollyConfiguration pollyConfiguration) {
    this.pollyConfiguration = pollyConfiguration;
  }

  public InputStream synthesize(String text, OutputFormat format) {
    // log.info("Default engine: {} " , pollyConfiguration.getDefaultEngine());

    SynthesizeSpeechRequest synthReq =
        SynthesizeSpeechRequest.builder().text(text).voiceId(
                pollyConfiguration.getVoice().id())
            .outputFormat(format).engine(
                pollyConfiguration.getDefaultEngine()).build();

    ResponseInputStream synthRes = pollyConfiguration.getPollyClient().synthesizeSpeech(
        synthReq);

    return synthRes;
  }

}
