package com.grupoingenios.sgpc.sgpc_api_final.dto.user;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RolResponseDTO {

    private Long id_rol;

    private String name;

    private LocalDateTime created_at;

    private LocalDateTime lastModifiedDate;


}
