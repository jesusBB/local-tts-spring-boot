package com.jaguarlandrover.d9.tts.localtts.service

import com.jaguarlandrover.d9.tts.localtts.configuration.S3Configuration
import com.jaguarlandrover.d9.tts.localtts.configuration.S3ConfigurationTest
import com.jaguarlandrover.d9.tts.localtts.services.PollyService
import com.jaguarlandrover.d9.tts.localtts.services.S3Service
import org.junit.jupiter.api.BeforeAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.TestContext
import org.testcontainers.containers.localstack.LocalStackContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.shaded.org.apache.commons.io.IOUtils
import org.testcontainers.shaded.org.bouncycastle.util.encoders.UTF8
import org.testcontainers.spock.Testcontainers
import org.testcontainers.utility.DockerImageName
import spock.lang.Specification

import java.nio.charset.StandardCharsets

import static org.testcontainers.containers.localstack.LocalStackContainer.Service.S3;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = [S3ConfigurationTest.class])
class S3ServiceSpecIt extends Specification{

    @Autowired
    S3Service s3Service

    @Container
    static LocalStackContainer localStack = new LocalStackContainer(
            DockerImageName.parse("localstack/localstack:3.0")
    );

    static final String BUCKET_NAME = UUID.randomUUID().toString();


    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("app.bucket", () -> BUCKET_NAME);
//        registry.add("app.queue", () -> QUEUE_NAME);
        registry.add(
                "spring.cloud.aws.region.static",
                () -> localStack.getRegion()
        );
        registry.add(
                "spring.cloud.aws.credentials.access-key",
                () -> localStack.getAccessKey()
        );
        registry.add(
                "spring.cloud.aws.credentials.secret-key",
                () -> localStack.getSecretKey()
        );
        registry.add(
                "s3.endpoint",
                () -> localStack.getEndpointOverride(S3).toString()
        );
       /* registry.add(
                "spring.cloud.aws.sqs.endpoint",
                () -> localStack.getEndpointOverride(SQS).toString()
        );*/
    }


    def setupSpec() {
        localStack.start()
//      localStack.execInContainer("awslocal", "s3", "mb", "s3://" + BUCKET_NAME)
        localStack.execInContainer("awslocal", "s3", "mb", "s3://" + "testbucket")
        System.out.println("AAAAAAAAAAA: " + localStack.execInContainer("awslocal", "s3", "ls").getStdout())
        System.out.println("S3 URL : " + localStack.getEndpointOverride(S3).toString())

    }

    def cleanupSpec(){
        localStack.stop()
    }

    def "test s3 uploading input stream"(){
        given: "an inputstream"
        String testData = "this is a beautiful test"
        InputStream inputStream = IOUtils.toInputStream(testData, StandardCharsets.UTF_8)
        System.out.println("starting test")

        when: "calling S3Service"
        String url = s3Service.putAudioInS3(inputStream)

        then: "Url has a value"
        System.out.println("URL: " + url)
        url !=  null
    }
}
