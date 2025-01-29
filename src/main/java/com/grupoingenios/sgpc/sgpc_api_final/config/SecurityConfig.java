package com.grupoingenios.sgpc.sgpc_api_final.config;

import com.grupoingenios.sgpc.sgpc_api_final.service.auth.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

/**
 * Configuración de seguridad para la aplicación.
 * Define las políticas de seguridad, autenticación basada en JWT y configuración de CORS.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configura la cadena de filtros de seguridad para la aplicación.
     * Incluye autenticación basada en JWT, desactivación de CSRF, manejo de CORS
     * y restricción de accesos a rutas según los roles.
     *
     * @param http                  Configuración de seguridad de HTTP.
     * @param jwtAuthenticationFilter Filtro de autenticación JWT.
     * @return Cadena de filtros de seguridad configurada.
     * @throws Exception Si ocurre algún error al construir la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)  // Desactiva CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/roles/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/users/**").hasRole("ADMIN")
                        .requestMatchers("/api/auth/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html").permitAll()  // Rutas públicas

                        .anyRequest().authenticated())  // Protege las demás rutas
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Añade filtro JWT
                .cors(cors -> cors.configurationSource(corsConfigurationSource())); // Configura CORS

        return http.build();
    }

    /**
     * Configura las reglas de CORS para permitir solicitudes desde orígenes específicos.
     *
     * @return Fuente de configuración de CORS con las reglas definidas.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://10.73.1.37:3000", "http://10.73.1.40:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Crea un filtro de autenticación basado en JWT.
     *
     * @param jwtUtil     Utilidad para manejar operaciones relacionadas con JWT.
     * @param tokenService Servicio para manejar la revocación de tokens.
     * @return Instancia configurada del filtro de autenticación JWT.
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtUtil jwtUtil, TokenService tokenService) {
        return new JwtAuthenticationFilter(jwtUtil, tokenService);
    }

    /**
     * Crea un codificador de contraseñas usando BCrypt.
     * Este codificador se utiliza para asegurar las contraseñas de los usuarios.
     *
     * @return Instancia del codificador de contraseñas BCrypt.
     */
    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
