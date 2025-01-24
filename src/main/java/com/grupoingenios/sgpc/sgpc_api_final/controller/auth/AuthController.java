package com.grupoingenios.sgpc.sgpc_api_final.controller.auth;
import com.grupoingenios.sgpc.sgpc_api_final.dto.auth.AuthResponse;
import com.grupoingenios.sgpc.sgpc_api_final.dto.auth.LoginRequest;
import com.grupoingenios.sgpc.sgpc_api_final.service.auth.AuthService;
import com.grupoingenios.sgpc.sgpc_api_final.service.auth.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para la autenticación y gestión de sesiones de usuarios.
 * Proporciona endpoints para iniciar y cerrar sesión utilizando tokens JWT.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final TokenService tokenService;

    /**
     * Constructor para inyectar los servicios de autenticación y gestión de tokens.
     *
     * @param authService Servicio para gestionar la autenticación de usuarios.
     * @param tokenService Servicio para manejar los tokens JWT, incluida su invalidación.
     */
    public AuthController(AuthService authService, TokenService tokenService) {
        this.authService = authService;
        this.tokenService = tokenService;
    }

    /**
     * Endpoint para iniciar sesión de un usuario.
     *
     * @param request Contiene las credenciales del usuario (nombre de usuario y contraseña).
     * @return Respuesta con el token JWT y el rol del usuario autenticado.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        String token = authService.login(request.getUsername(), request.getPassword());
        String role = authService.extractRoleFromToken(token);
        return ResponseEntity.ok(new AuthResponse(token, role));
    }

    /**
     * Endpoint para cerrar sesión de un usuario.
     * Invalida el token JWT recibido en el encabezado de la solicitud.
     *
     * @param authorizationHeader Encabezado "Authorization" con el token JWT.
     * @return Mensaje indicando el estado de la operación.
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                throw new IllegalArgumentException("Token no proporcionado o inválido");
            }
            // Obtener el token del encabezado
            String token = authorizationHeader.substring(7);

            // Invalidar el token
            tokenService.invalidateToken(token);

            return ResponseEntity.ok("Sesión cerrada");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(); // Depuración
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error inesperado");
        }
    }

}
