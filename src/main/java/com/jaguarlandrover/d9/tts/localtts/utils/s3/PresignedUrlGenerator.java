package com.jaguarlandrover.d9.tts.localtts.utils.s3;

import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.awscore.presigner.PresignedRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

@Slf4j
public class PresignedUrlGenerator {
  public static String generatePresignedUrl(String bucketName, String objectKey) {
    S3Presigner presigner = S3Presigner.create();
    GetObjectRequest
        getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(
        objectKey).build();

    GetObjectPresignRequest
        getObjectPresignRequest = GetObjectPresignRequest.builder().signatureDuration(
        Duration.ofMinutes(5)).getObjectRequest(getObjectRequest).build();

    PresignedRequest presignedRequest = presigner.presignGetObject(
        getObjectPresignRequest);

    log.info("Presigned URL: " + presignedRequest.url().toString());

    return presignedRequest.url().toString();
  }

}
