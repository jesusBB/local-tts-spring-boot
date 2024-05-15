package com.jaguarlandrover.d9.tts.localtts.services;

import com.jaguarlandrover.d9.tts.localtts.configuration.S3Configuration;
import com.jaguarlandrover.d9.tts.localtts.utils.s3.PresignedUrlGenerator;
import java.io.IOException;
import java.io.InputStream;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@Slf4j
@Getter
public class S3Service {

  private final S3Client s3;

  private S3Configuration s3Configuration;

  @Autowired
  public S3Service(S3Client s3, S3Configuration s3Configuration) {
    this.s3 = s3;
    this.s3Configuration = s3Configuration;
  }


  public String putAudioInS3(InputStream speechStream) {
    String bucketName = s3Configuration.getAudioDefaultBucket();

    final String objectKey = "example-1.mp3";

    PutObjectRequest objectRequest = PutObjectRequest.builder().bucket(
        bucketName).key(objectKey).build();

    try {
      s3.putObject(objectRequest,
          RequestBody.fromBytes(speechStream.readAllBytes()));
    } catch (IOException e) {
      log.error("Error uploading audio file to S3: {}", e.getMessage());
      throw new RuntimeException(e);
    }

    return PresignedUrlGenerator.generatePresignedUrl(bucketName, objectKey);
  }


}
