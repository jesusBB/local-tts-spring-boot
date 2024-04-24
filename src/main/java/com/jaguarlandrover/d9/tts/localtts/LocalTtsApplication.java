package com.jaguarlandrover.d9.tts.localtts;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LocalTtsApplication {


	public static void main(String[] args) {
		SpringApplication.run(LocalTtsApplication.class, args);
	}

}
