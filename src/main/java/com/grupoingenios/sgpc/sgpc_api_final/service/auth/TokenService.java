package com.grupoingenios.sgpc.sgpc_api_final.service.auth;


import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

/**
 * Servicio de manejo de tokens revocados.
 * Permite invalidar tokens y verificar si un token ha sido revocado.
 */
@Service
public class TokenService {

    /**
     * Conjunto que almacena los tokens revocados.
     */
    private final Set<String> revokedTokens = new HashSet<>();

    /**
     * Invalida un token, agregándolo al conjunto de tokens revocados.
     *
     * @param token El token que se desea invalidar.
     */
    public void invalidateToken(String token) {
        revokedTokens.add(token);
    }

    /**
     * Verifica si un token ha sido revocado.
     *
     * @param token El token a verificar.
     * @return `true` si el token ha sido revocado, `false` en caso contrario.
     */
    public boolean isTokenRevoked(String token) {
        return revokedTokens.contains(token);
    }

}
