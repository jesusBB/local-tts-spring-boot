package com.jaguarlandrover.d9.tts.localtts.configuration

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.localstack.LocalStackContainer
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client

@TestConfiguration
@ActiveProfiles("test")
class S3ConfigurationTest {

    @Bean
    public S3Client s3Client() {
        // Define your mock or test version of the S3Client bean here
        return S3Client.builder().region(Region.of("eu-west-1"))
                                 .endpointOverride(URI.create("http://localhost:4566"))
                                 .forcePathStyle(true)
                                 .build();
    }
}
