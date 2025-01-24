package com.grupoingenios.sgpc.sgpc_api_final.entity.inventory;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.grupoingenios.sgpc.sgpc_api_final.entity.work.Work;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Representa un proveedor en el sistema.
 * Un proveedor tiene información como el nombre, la dirección, el teléfono, el correo electrónico y el RFC.
 * Además, puede estar asociado a múltiples inventarios y trabajos.
 */
@Entity
@Setter
@Getter
@Table(name = "supplier")
@EntityListeners(AuditingEntityListener.class)
public class Supplier {

    /**
     * Identificador único del proveedor.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_supplier;

    /**
     * Nombre del proveedor.
     */
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    /**
     * Dirección del proveedor.
     */
    @Column(name = "address", length = 100, nullable = false)
    private String address;

    /**
     * Teléfono del proveedor.
     */
    @Column(name = "phone", length = 10, nullable = false)
    private String phone;

    /**
     * Correo electrónico del proveedor.
     */
    @Column(name = "email", length = 100, nullable = false)
    private String email;

    /**
     * RFC del proveedor.
     */
    @Column(name = "rfc", length = 13, nullable = false)
    private String rfc;

    /**
     * Relación muchos a muchos con la entidad `Inventory`.
     * Un proveedor puede estar asociado a varios inventarios.
     */
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "suppliers")
    @JsonBackReference("inventory-supplier")
    private Set<Inventory> inventories = new HashSet<>();

    /**
     * Relación muchos a muchos con la entidad `Work`.
     * Un proveedor puede estar asociado a varios trabajos.
     */

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "suppliers")
    private Set<Work> works = new HashSet<>();

    /**
     * Fecha de creación del proveedor, gestionada automáticamente por Spring Data JPA.
     */
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created_at;

    /**
     * Fecha de última modificación del proveedor, gestionada automáticamente por Spring Data JPA.
     */
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

}
