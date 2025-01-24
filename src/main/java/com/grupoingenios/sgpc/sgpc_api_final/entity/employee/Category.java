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
 * Representa una categoría de empleados en el sistema.
 * Esta entidad contiene el nombre, la descripción y la lista de empleados asociados a esta categoría.
 */
@Entity
@Getter
@Setter
@Table(name = "category")
@EntityListeners(AuditingEntityListener.class)
public class Category {

    /**
     * Identificador único de la categoría.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategory;

    /**
     * Nombre de la categoría.
     */
    @Column(name = "name", length = 80, nullable = false)
    private String name;

    /**
     * Descripción de la categoría.
     */
    @Column(name = "description", length = 255, nullable = false)
    private String description;

    /**
     * Fecha de creación de la categoría, gestionada automáticamente por Spring Data JPA.
     */
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created_at;

    /**
     * Fecha de última modificación de la categoría, gestionada automáticamente por Spring Data JPA.
     */
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;


    /**
     * Relación uno a muchos con la entidad Employee.
     * Una categoría puede tener varios empleados asociados.
     */
    @OneToMany(mappedBy = "category", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = false)
    @JsonManagedReference("category-employee")
    private List<Employee> employees = new ArrayList<>();


}