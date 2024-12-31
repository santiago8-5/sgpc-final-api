package com.grupoingenios.sgpc.sgpc_api_final.service.auth;


import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

@Service
public class TokenService {

    private final Set<String> revokedTokens = new HashSet<>();

    // Método para invalidar un token
    public void invalidateToken(String token) {
        revokedTokens.add(token);
    }

    // Método para verificar si un token está revocado
    public boolean isTokenRevoked(String token) {
        return revokedTokens.contains(token);
    }

}
