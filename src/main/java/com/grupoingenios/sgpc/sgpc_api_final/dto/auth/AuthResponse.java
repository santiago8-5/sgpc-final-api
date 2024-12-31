package com.grupoingenios.sgpc.sgpc_api_final.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String role;
}
