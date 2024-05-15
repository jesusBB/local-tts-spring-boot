package com.jaguarlandrover.d9.tts.localtts.exceptions;

import com.jaguarlandrover.d9.tts.localtts.model.TtsErrorResponse;
import java.net.HttpURLConnection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
  @ExceptionHandler(Exception.class)
  public ResponseEntity<TtsErrorResponse> handleException(Exception ex) {
    log.error(ex.getMessage());
    return ResponseEntity.status(HttpURLConnection.HTTP_INTERNAL_ERROR).body(
        TtsErrorResponse.builder().message(
            "Ups .. Something went wrong").build());
  }
}
