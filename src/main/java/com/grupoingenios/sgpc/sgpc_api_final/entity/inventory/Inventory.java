package com.grupoingenios.sgpc.sgpc_api_final.entity.inventory;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
 * La entidad `Inventory` representa un insumo o material dentro del sistema de inventarios.
 * Un inventario tiene atributos como el nombre, la cantidad disponible, la descripción, el precio, y la unidad.
 * Además, puede estar relacionado con múltiples proveedores.
 */
@Entity
@Setter
@Getter
@Table(name = "inventory")
@EntityListeners(AuditingEntityListener.class)
public class Inventory {

    /**
     * Identificador único del inventario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_inventory;

    /**
     * Nombre del inventario.
     */
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    /**
     * Cantidad disponible del inventario.
     */
    @Column(name = "amount", nullable = false)
    private int amount;

    /**
     * Descripción del inventario.
     */
    @Column(name = "description", length = 255)
    private String description;

    /**
     * Precio del inventario.
     */
    @Column(name = "price")
    private Float price;

    /**
     * Tipo de unidad de medida del inventario.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "unit", nullable = false)
    private UnitType unit;

    /**
     * Tipo de insumo del inventario.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "inputType", nullable = false)
    private InputType inputType;

    /**
     * Nombre de la bodega donde se encuentra el inventario.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "wineryName", nullable = false)
    private WineryName wineryName;

    /**
     * Proveedores asociados al inventario.
     * Relación muchos a muchos con la entidad `Supplier`.
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference("inventory-supplier")
    @JoinTable(
            name = "inventory_supplier",
            joinColumns = @JoinColumn(name = "inventory_id"),
            inverseJoinColumns = @JoinColumn(name = "supplier_id")
    )
    private Set<Supplier> suppliers = new HashSet<>();

    /**
     * Fecha de creación del inventario, gestionada automáticamente por Spring Data JPA.
     */
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created_at;

    /**
     * Fecha de última modificación del inventario, gestionada automáticamente por Spring Data JPA.
     */
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

}