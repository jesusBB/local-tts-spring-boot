package com.jaguarlandrover.d9.tts.localtts.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TtsErrorResponse {

    private String message;

}
