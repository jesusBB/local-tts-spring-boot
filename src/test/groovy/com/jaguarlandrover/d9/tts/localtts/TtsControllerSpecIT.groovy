package com.jaguarlandrover.d9.tts.localtts

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get as mockMvcGet
import spock.lang.Specification

@AutoConfigureMockMvc
@SpringBootTest
class TtsControllerSpecIT extends Specification{

    @Autowired
    MockMvc mockMvc;

    @Autowired


    def "when some text is sent in the request a fileUrl is sent to the response"(){

        when: "a request is made"
        def response = mockMvc.perform(mockMvcGet("/api/polly").header("text", "this is a test"))


    }



}
