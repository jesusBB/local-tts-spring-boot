package com.jaguarlandrover.d9.tts.localtts.exceptions;

import com.jaguarlandrover.d9.tts.localtts.model.TtsErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;

import java.net.HttpURLConnection;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<TtsErrorResponse> handleException(Exception ex) {
        System.out.println(ex.getMessage());
        return ResponseEntity.status(HttpURLConnection.HTTP_INTERNAL_ERROR).body(TtsErrorResponse.builder().message("Ups .. Something went wrong").build());
    }
}
