package com.jaguarlandrover.d9.tts.localtts.services;

import com.amazonaws.regions.Regions;

import com.jaguarlandrover.d9.tts.localtts.configuration.PollyConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.polly.PollyClient;
import software.amazon.awssdk.services.polly.model.DescribeVoicesRequest;
import software.amazon.awssdk.services.polly.model.OutputFormat;
import software.amazon.awssdk.services.polly.model.SynthesizeSpeechRequest;
import software.amazon.awssdk.services.polly.model.Voice;
import software.amazon.awssdk.services.polly.model.VoiceId;

@Slf4j
@Service
public class PollyService {
    //private PollyClient polly;
    private PollyConfiguration pollyConfiguration;
    //private Voice voice;

    @Autowired
    public PollyService(PollyConfiguration pollyConfiguration){
//        this.polly = polly;
        this.pollyConfiguration = pollyConfiguration;
//        this.voice = voice;
    }

    public InputStream synthesize(String text, OutputFormat format) {
       // log.info("Default engine: {} " , pollyConfiguration.getDefaultEngine());

        SynthesizeSpeechRequest synthReq =
                SynthesizeSpeechRequest.builder().text(text).voiceId(pollyConfiguration.getVoice().id())
                        .outputFormat(format).engine(pollyConfiguration.getDefaultEngine()).build();

        ResponseInputStream synthRes = pollyConfiguration.getPollyClient().synthesizeSpeech(synthReq);

        return synthRes;
    }

}
