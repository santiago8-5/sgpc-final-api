package com.grupoingenios.sgpc.sgpc_api_final.config;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Utilidad para manejar operaciones relacionadas con tokens JWT.
 * Proporciona métodos para generar, extraer y validar tokens JWT.
 */
@Component
@Configuration
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    /**
     * Genera la clave de firma a partir del secreto configurado.
     *
     * @return Clave de firma para validar los tokens JWT.
     */
    private SecretKey getSigningKey() {
        // Convertimos el secreto en una clave válida
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Genera un token JWT basado en el nombre de usuario y el rol.
     *
     * @param username Nombre de usuario para incluir en el token.
     * @param role     Rol del usuario para incluir como un reclamo.
     * @return Token JWT generado.
     */
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extrae el nombre de usuario del token JWT.
     *
     * @param token Token JWT del cual extraer el nombre de usuario.
     * @return Nombre de usuario contenido en el token.
     */
    public String extractUsername(String token) {
        return Jwts.parser()// Usamos parserBuilder en lugar de parser
                .setSigningKey(getSigningKey()) // Proporcionamos la clave adecuada
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Extrae el rol del usuario contenido en el token JWT.
     *
     * @param token Token JWT del cual extraer el rol.
     * @return Rol contenido en el token.
     */
    public String extractRole(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

    /**
     * Valida el token JWT asegurándose de que no esté expirado y sea legítimo.
     *
     * @param token Token JWT a validar.
     * @return {@code true} si el token es válido, de lo contrario {@code false}.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }


}
