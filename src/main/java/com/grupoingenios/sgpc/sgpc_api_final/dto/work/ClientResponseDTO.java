package com.grupoingenios.sgpc.sgpc_api_final.dto.work;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * DTO que representa la respuesta con información detallada de un cliente.
 * Incluye atributos como identificador, nombre, dirección, teléfono, correo electrónico, RFC y marcas de tiempo.
 */
@Setter
@Getter
public class ClientResponseDTO {

    private Long idClient;

    private String name;

    private String address;

    private String municipality;

    private String state;

    private String phone;

    private String email;

    private String rfc;

    private LocalDateTime created_at;

    private LocalDateTime lastModifiedDate;

}
