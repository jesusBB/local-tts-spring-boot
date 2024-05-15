package com.jaguarlandrover.d9.tts.localtts.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@Getter
@Setter
public class S3Configuration {

  @Value("${s3.audio.bucket.default}")
  private String audioDefaultBucket;

  @Value("${region.default}")
  private String defaultRegion;

  @Autowired
  private AWSCredentialsConfiguration awsCredentialsConfiguration;

  @Bean
  public S3Client s3Client() {
//        return AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(defaultRegion)).build();
    return S3Client.builder().region(Region.of(defaultRegion)).credentialsProvider(
        awsCredentialsConfiguration.awsCredentialsProvider()).build();
  }
}
