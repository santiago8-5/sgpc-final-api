package com.grupoingenios.sgpc.sgpc_api_final.dto.error;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;

/**
 * DTO que representa la respuesta estándar para errores en la aplicación.
 * Incluye detalles sobre el mensaje de error, el código del error y una marca de tiempo.
 */
@Getter
@Setter
@NoArgsConstructor
public class ErrorResponseDTO {

    /**
     * Mensaje descriptivo del error.
     */
    private String message;

    /**
     * Código del error, útil para identificar tipos específicos de errores.
     */
    private String errorCode;

    /**
     * Marca de tiempo en formato ISO 8601, que indica cuándo ocurrió el error.
     */
    private String timestamp;


    /**
     * Constructor que inicializa el mensaje y el código del error.
     * También asigna automáticamente la marca de tiempo actual.
     *
     * @param message   Mensaje descriptivo del error.
     * @param errorCode Código del error.
     */
    public ErrorResponseDTO(String message, String errorCode) {
        this.message = message;
        this.errorCode = errorCode;
        this.timestamp = Instant.now().toString();
    }

}
