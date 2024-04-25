package com.jaguarlandrover.d9.tts.localtts.configuration;

import com.amazonaws.regions.Regions;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.polly.PollyClient;
import software.amazon.awssdk.services.polly.model.DescribeVoicesRequest;
import software.amazon.awssdk.services.polly.model.Voice;

@Configuration
@Getter
public class PollyConfiguration {

    @Value("${polly.region.default}")
    private String defaultRegion;

    @Value("${polly.engine.default}")
    private String defaultEngine;

    @Bean
    public PollyClient getPollyClient(){
        return PollyClient.builder().region(Region.of(defaultRegion)).build();
    }

    @Bean
    public Voice getVoice(){
        DescribeVoicesRequest describeVoicesRequest =  DescribeVoicesRequest.builder().engine(defaultEngine).build();
//        getPollyClient().describeVoices(describeVoicesRequest).voices().stream().forEach(v -> log.info(v.name()));
        return getPollyClient().describeVoices(describeVoicesRequest).voices().stream().filter(v -> v.name().equals("Amy")).findFirst().orElseThrow(() -> new RuntimeException("Voice not found"));

    }

}
