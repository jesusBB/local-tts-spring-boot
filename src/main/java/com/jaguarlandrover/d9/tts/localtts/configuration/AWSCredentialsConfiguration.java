package com.jaguarlandrover.d9.tts.localtts.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;

@Configuration
@Getter
public class AWSCredentialsConfiguration {

  @Value("${AWS_ACCESS_KEY_ID}")
  private String accessKeyId;

  @Value("${AWS_SECRET_ACCESS_KEY}")
  private String secretAccessKey;

  @Bean
  public AwsCredentialsProvider awsCredentialsProvider() {
    System.out.println("CREDENTIALS: " + accessKeyId);

    AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(accessKeyId,
        secretAccessKey);
    return StaticCredentialsProvider.create(awsCredentials);
  }
}
