package com.grupoingenios.sgpc.sgpc_api_final.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


/**
 * Configuración para habilitar la auditoría en JPA.
 * Permite registrar automáticamente información de auditoría como
 * fechas de creación y modificación en las entidades.
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
    // Clase de configuración sin lógica adicional, centrada en habilitar JPA Auditing.
}
