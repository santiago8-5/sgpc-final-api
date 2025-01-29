package com.grupoingenios.sgpc.sgpc_api_final.service.auth;

import com.grupoingenios.sgpc.sgpc_api_final.config.JwtUtil;
import com.grupoingenios.sgpc.sgpc_api_final.entity.user.User;
import com.grupoingenios.sgpc.sgpc_api_final.exception.AccessDeniedException;
import com.grupoingenios.sgpc.sgpc_api_final.exception.IncorrectPasswordException;
import com.grupoingenios.sgpc.sgpc_api_final.exception.ResourceNotFoundException;
import com.grupoingenios.sgpc.sgpc_api_final.repository.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.grupoingenios.sgpc.sgpc_api_final.constants.AppConstant.*;

/**
 * Servicio de autenticación.
 * Proporciona métodos para la autenticación de usuarios y la generación de tokens JWT.
 */
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor que inyecta las dependencias necesarias para el servicio.
     *
     * @param userRepository Repositorio de usuarios.
     * @param jwtUtil Utilidad para la generación y validación de tokens JWT.
     * @param passwordEncoder Codificador de contraseñas.
     */
    public AuthService(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }


    /**
     * Realiza el inicio de sesión de un usuario verificando su nombre de usuario y contraseña.
     * Si la autenticación es exitosa, se genera un token JWT.
     *
     * @param username Nombre de usuario del usuario.
     * @param password Contraseña del usuario.
     * @return Token JWT generado.
     * @throws ResourceNotFoundException Si el usuario no existe.
     * @throws IncorrectPasswordException Si la contraseña proporcionada es incorrecta.
     */
    public String login(String username, String password) {
        User user = userRepository.findByUsernameWithRol(username)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));

        String role = user.getRol().getName();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IncorrectPasswordException(INCORRECT_PASSWORD);
        }

        return jwtUtil.generateToken(username, role);
    }

    /**
     * Extrae el rol del usuario desde un token JWT.
     *
     * @param token Token JWT del que se extraerá el rol.
     * @return El rol del usuario extraído del token.
     */
    public String extractRoleFromToken(String token) {
        return jwtUtil.extractRole(token);
    }


}
