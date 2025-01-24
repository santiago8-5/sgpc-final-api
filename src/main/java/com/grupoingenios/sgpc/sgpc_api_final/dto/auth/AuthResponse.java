package com.grupoingenios.sgpc.sgpc_api_final.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa la respuesta de autenticación.
 * Contiene el token JWT generado y el rol del usuario autenticado.
 */
@Setter
@Getter
@AllArgsConstructor
public class AuthResponse {
    /**
     * Token JWT generado tras una autenticación exitosa.
     */
    private String token;

    /**
     * Rol del usuario autenticado.
     */
    private String role;
}
