package com.grupoingenios.sgpc.sgpc_api_final.dto.user;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseDTO {

    private Long id_user;

    private String username;

    private String password;

    private String rolName;

    private LocalDateTime created_at;

    private LocalDateTime lastModifiedDate;

}
