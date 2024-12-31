package com.grupoingenios.sgpc.sgpc_api_final.dto.error;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponseDTO {

    private String message;
    private String errorCode;
    private String timestamp;

    public ErrorResponseDTO(String message, String errorCode) {
        this.message = message;
        this.errorCode = errorCode;
        this.timestamp = Instant.now().toString();
    }

}
