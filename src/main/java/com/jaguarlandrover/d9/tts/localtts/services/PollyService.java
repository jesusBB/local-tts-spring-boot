package com.jaguarlandrover.d9.tts.localtts.services;

import com.amazonaws.regions.Regions;

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

@Service
public class PollyService {
    private PollyClient polly = PollyClient.builder().region(Region.EU_WEST_1).build();

    public InputStream synthesize(String text, OutputFormat format) {
        DescribeVoicesRequest describeVoicesRequest =  DescribeVoicesRequest.builder().engine("neural").build();
        polly.describeVoices(describeVoicesRequest).voices().stream().forEach(v -> System.out.println(v.name()));
        Voice voice = polly.describeVoices(describeVoicesRequest).voices().stream().filter(v -> v.name().equals("Matthew")).findFirst().orElseThrow(() -> new RuntimeException("Voice not found"));
        SynthesizeSpeechRequest synthReq =
                SynthesizeSpeechRequest.builder().text(text).voiceId(voice.id())
                        .outputFormat(format).engine("neural").build();

        ResponseInputStream synthRes = polly.synthesizeSpeech(synthReq);

        return synthRes;
    }

}
