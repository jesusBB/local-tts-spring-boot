package com.jaguarlandrover.d9.tts.localtts.services;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.polly.AmazonPolly;
import com.amazonaws.services.polly.AmazonPollyClientBuilder;
import com.amazonaws.services.polly.model.OutputFormat;
import com.amazonaws.services.polly.model.SynthesizeSpeechRequest;
import com.amazonaws.services.polly.model.SynthesizeSpeechResult;
import com.amazonaws.services.polly.model.VoiceId;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class PollyService {

    private AmazonPolly polly = AmazonPollyClientBuilder.standard().withRegion(Regions.EU_WEST_1).build();

    public InputStream synthesize(String text, OutputFormat format) {
        SynthesizeSpeechRequest synthReq =
                new SynthesizeSpeechRequest().withText(text).withVoiceId("asdf")
                        .withOutputFormat(format).withEngine("neural");
        SynthesizeSpeechResult synthRes;
        synthRes = polly.synthesizeSpeech(synthReq);

        return synthRes.getAudioStream();
    }

}
