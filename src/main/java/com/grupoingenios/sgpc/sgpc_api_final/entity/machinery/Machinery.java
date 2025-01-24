package com.grupoingenios.sgpc.sgpc_api_final.entity.machinery;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Representa una maquinaria dentro del sistema.
 * La entidad `Machinery` contiene información sobre una herramienta o equipo utilizado en las obras.
 * Los atributos incluyen nombre, marca, modelo, serial, fecha de adquisición, estado y tipo de herramienta.
 */
@Entity
@Getter
@Setter
@Table(name = "machinery")
@EntityListeners(AuditingEntityListener.class)
public class Machinery {

    /**
     * Identificador único de la maquinaria.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_machinery;

    /**
     * Nombre de la maquinaria.
     */
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /**
     * Marca de la maquinaria.
     */
    @Column(name = "brand", nullable = false, length = 50)
    private String brand;

    /**
     * Modelo de la maquinaria.
     */
    @Column(name = "model", nullable = false, length = 4)
    private int model;

    /**
     * Número de serie de la maquinaria.
     */
    @Column(name = "serial", nullable = false, length = 17)
    private String serial;

    /**
     * Fecha de adquisición de la maquinaria.
     */
    @Column(name = "acquisitionDate")
    private LocalDate acquisitionDate;

    /**
     * Estado actual de la maquinaria.
     */
    @Enumerated(EnumType.STRING)
    private StatusTypeMachinery status;

    /**
     * Tipo de herramienta de la maquinaria.
     */
    @Enumerated(EnumType.STRING)
    private ToolType toolType;

    /**
     * Fecha de creación de la maquinaria, gestionada automáticamente por Spring Data JPA.
     */
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created_at;

    /**
     * Fecha de última modificación de la maquinaria, gestionada automáticamente por Spring Data JPA.
     */
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;


}