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

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }


    public String login(String username, String password) {
        User user = userRepository.findByUsernameWithRol(username)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));

        String role = user.getRol().getName();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IncorrectPasswordException(INCORRECT_PASSWORD);
        }

        return jwtUtil.generateToken(username, role);
    }

    public String extractRoleFromToken(String token) {
        return jwtUtil.extractRole(token);
    }


}
