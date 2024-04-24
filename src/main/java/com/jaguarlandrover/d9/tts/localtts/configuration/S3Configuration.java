package com.jaguarlandrover.d9.tts.localtts.configuration;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Configuration {

    @Value("${region.default}")
    private String defaultRegion;

    @Bean
    public AmazonS3 amazonS3Client(){
        return AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(defaultRegion)).build();
    }
}
