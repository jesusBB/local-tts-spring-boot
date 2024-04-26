package com.jaguarlandrover.d9.tts.localtts

import com.jaguarlandrover.d9.tts.localtts.services.PollyService
import com.jaguarlandrover.d9.tts.localtts.services.S3Service
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get as mockMvcGet
import spock.lang.Specification

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest
//@WebMvcTest
@Slf4j
@ActiveProfiles(["test"])
class TtsControllerSpecIT extends Specification{

    @Autowired
    MockMvc mockMvc;


    def "when some text is sent in the request a fileUrl is sent to the response"(){
        given: "nothing"
        System.out.println("asdf")
        //def mockS3Service = Mock(S3Service)
        //def mockPollyService = Mock(PollyService)

        when: "a request is made"
        def response = mockMvc.perform(mockMvcGet("/api/polly/").header("text", "this is a test"))

        then: "a fileUrl is expected"
        System.out.println("response: " + response.andReturn().response.contentAsString)
        response.andExpect(status().isOk())


    }



}
