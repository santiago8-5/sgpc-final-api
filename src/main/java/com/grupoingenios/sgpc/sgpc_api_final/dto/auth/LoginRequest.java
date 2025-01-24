package com.grupoingenios.sgpc.sgpc_api_final.dto.auth;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa la solicitud de inicio de sesión.
 * Contiene las credenciales del usuario que desea autenticarse.
 */
@Getter
@Setter
public class LoginRequest {
    /**
     * Nombre de usuario proporcionado para la autenticación.
     */
    private String username;

    /**
     * Contraseña proporcionada para la autenticación.
     */
    private String password;
}
