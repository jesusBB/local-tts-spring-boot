package com.jaguarlandrover.d9.tts.localtts.controller

import com.jaguarlandrover.d9.tts.localtts.configuration.S3ConfigurationTest
import com.jaguarlandrover.d9.tts.localtts.services.PollyService
import com.jaguarlandrover.d9.tts.localtts.services.S3Service
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.testcontainers.containers.localstack.LocalStackContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.shaded.org.apache.commons.io.IOUtils
import org.testcontainers.spock.Testcontainers
import org.testcontainers.utility.DockerImageName
import software.amazon.awssdk.services.polly.model.OutputFormat

import java.nio.charset.StandardCharsets

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get as mockMvcGet
import spock.lang.Specification

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.S3

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = [S3ConfigurationTest.class])
@Testcontainers
class TtsControllerSpecIT extends Specification{

    @Autowired
    MockMvc mockMvc

    @Autowired
    S3Service s3Service

    TtsController ttsController

    PollyService pollyService = Mock(PollyService.class)



    @Container
    static LocalStackContainer localStack = new LocalStackContainer(
            DockerImageName.parse("localstack/localstack:3.0"))

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
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
    }

    def setupSpec() {
        localStack.start()
        localStack.execInContainer("awslocal", "s3", "mb", "s3://" + "testbucket")
    }

    //@TestContainers will automatically stop the containers started by this IT
    /*def cleanupSpec(){
        localStack.stop()
    }*/


    def "when some text is sent in the request a fileUrl is sent to the response"(){
        given: "pollyService returns an input stream"
        String testData = "this is a beautiful test"
        def inputStream = IOUtils.toInputStream(testData, StandardCharsets.UTF_8)
        pollyService.toString(testData, OutputFormat.MP3) >> inputStream

        when: "a request is made"
        def response = mockMvc.perform(mockMvcGet("/api/polly/").header("text", "this is a test"))

        then: "a fileUrl is expected"
        System.out.println("response: " + response.andReturn().response.contentAsString)
        response.andExpect(status().isOk())


    }



}
