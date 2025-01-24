package com.grupoingenios.sgpc.sgpc_api_final.config;

import com.grupoingenios.sgpc.sgpc_api_final.service.auth.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

/**
 * Filtro de autenticación basado en JWT para Spring Security.
 * Se ejecuta una vez por solicitud y valida el token JWT presente en el encabezado "Authorization".
 */
@Getter
@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final TokenService tokenService;

    /**
     * Constructor para inyectar dependencias de utilidad para manejo de JWT y servicio de tokens.
     *
     * @param jwtUtil     Clase de utilidades para operaciones con tokens JWT.
     * @param tokenService Servicio para manejar tokens, incluyendo revocación.
     */
    public JwtAuthenticationFilter(JwtUtil jwtUtil, TokenService tokenService) {
        this.jwtUtil = jwtUtil;
        this.tokenService = tokenService;
    }

    /**
     * Método principal del filtro que valida el token JWT en cada solicitud HTTP.
     *
     * @param request  Solicitud HTTP entrante.
     * @param response Respuesta HTTP saliente.
     * @param chain    Cadena de filtros para continuar con el procesamiento de la solicitud.
     * @throws ServletException Si ocurre un error durante el procesamiento del filtro.
     * @throws IOException      Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            // Verifica si el token está revocado
            if (tokenService.isTokenRevoked(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token revocado");
                return;
            }

            // Extrae información del token JWT
            String username = jwtUtil.extractUsername(token);
            String role = jwtUtil.extractRole(token);

            // Valida el token y asigna la autenticación al contexto de seguridad
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null && jwtUtil.validateToken(token)) {

                    // Asigna el rol como una autoridad para Spring Security
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role); //
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            username, null, List.of(authority));
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                }

        }
        // Continúa con la ejecución de la cadena de filtros
        chain.doFilter(request, response);
    }

}
