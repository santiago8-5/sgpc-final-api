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
 * La clase `Position` representa un puesto dentro de la organización.
 * Esta entidad se utiliza para gestionar los distintos tipos de puestos de trabajo
 * dentro de la empresa, y está relacionada con la entidad `Employee` a través de una relación uno a muchos.
 */
@Entity
@Getter
@Setter
@Table(name="position")
@EntityListeners(AuditingEntityListener.class)
public class Position {

    /**
     * Identificador único para el puesto de trabajo.
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idPosition;

    /**
     * Nombre del puesto de trabajo.
     * Es un campo obligatorio y tiene una longitud máxima de 80 caracteres.
     */
    @Column(name="name", nullable=true, length=80)
    private String name;


    /**
     * Descripción del puesto de trabajo.
     * Es un campo opcional con una longitud máxima de 255 caracteres.
     */
    @Column(name = "description", nullable = true, length = 255)
    private String description;

    /**
     * Estado del puesto de trabajo.
     * Utiliza un tipo enumerado (`StatusType`) para definir el estado (por ejemplo, activo, inactivo).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusType statusType;

    /**
     * Fecha y hora de creación del puesto.
     * Este campo se establece automáticamente cuando el puesto se crea en la base de datos.
     */
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created_at;

    /**
     * Fecha y hora de la última modificación del puesto.
     * Este campo se actualiza automáticamente cuando se realizan cambios en el puesto.
     */
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    /**
     * Lista de empleados asociados a este puesto de trabajo.
     * La relación es de uno a muchos, lo que significa que un puesto puede tener múltiples empleados asignados.
     */
    @OneToMany(mappedBy="position", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = false)
    @JsonManagedReference("position-employee")
    private List<Employee> employees = new ArrayList<>();

}
