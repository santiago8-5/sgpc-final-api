package com.grupoingenios.sgpc.sgpc_api_final.entity.employee;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un departamento dentro de la organización.
 * Cada departamento tiene un nombre, una descripción, un correo electrónico y una lista de empleados asignados.
 */
@Setter
@Getter
@Entity
@Table(name = "department")
@EntityListeners(AuditingEntityListener.class)
public class Department {

    /**
     * Identificador único del departamento.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long idDepartment;

    /**
     * Nombre del departamento.
     */
    @Column(name = "name", nullable = false, length = 80)
    private String name;

    /**
     * Descripción del departamento.
     */
    @Column(name = "description", nullable = true, length = 255)
    private String description;

    /**
     * Correo electrónico asociado al departamento.
     */
    @Column(name = "email", nullable = true, length = 100)
    private String email;

    /**
     * Fecha de creación del departamento, gestionada automáticamente por Spring Data JPA.
     */
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created_at;

    /**
     * Fecha de última modificación del departamento, gestionada automáticamente por Spring Data JPA.
     */
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    /**
     * Relación uno a muchos con la entidad PlantEmployee.
     * Un departamento puede tener varios empleados asociados.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "department", orphanRemoval = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference("department-plant")
    private List<PlantEmployee> employees = new ArrayList<>();
}
