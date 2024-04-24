package com.jaguarlandrover.d9.tts.localtts.services;

import com.amazonaws.HttpMethod;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;

@Service
public class S3Service {

    private final AmazonS3 s3;

    @Autowired
    public S3Service(AmazonS3 s3) {
        this.s3 = s3;
    }

    public String putAudioInS3(InputStream speechStream) {
        String bucketName = "tts-workshop2";

        // s3.putObject("tts", "example-1", speechStream, new ObjectMetadata());
        final String objectKey = "example-1.mp3";
        PutObjectRequest objectRequest = new PutObjectRequest(bucketName, objectKey, speechStream, new ObjectMetadata()).withInputStream(speechStream);//.withCannedAcl(CannedAccessControlList.PublicRead);

        s3.putObject(objectRequest);

        final Calendar instance = Calendar.getInstance();

        instance.add(Calendar.MINUTE, 1);


        GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(bucketName, objectKey)
                .withMethod(HttpMethod.GET).withExpiration(instance.getTime());

        URL presignedUrl = s3.generatePresignedUrl(urlRequest);

        String urlString = s3.getUrl("tts-workshop2", objectKey).toString();
        return presignedUrl.toString();

    }

}
