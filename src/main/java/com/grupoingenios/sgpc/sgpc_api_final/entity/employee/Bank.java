package com.grupoingenios.sgpc.sgpc_api_final.entity.employee;

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
 * Representa un banco en el sistema.
 * Esta entidad contiene la información básica sobre un banco, como su nombre y la lista de cuentas asociadas.
 */
@Entity
@Setter
@Getter
@Table(name = "bank")
@EntityListeners(AuditingEntityListener.class)
public class Bank {

    /**
     * Identificador único del banco.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBank;

    /**
     * Nombre del banco.
     */
    @Column(name = "name", nullable = false, length = 80)
    private String name;

    /**
     * Fecha de creación del banco. Esta propiedad se gestiona automáticamente por Spring Data JPA.
     */
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created_at;

    /**
     * Fecha de la última modificación del banco. Esta propiedad se gestiona automáticamente por Spring Data JPA.
     */
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    /**
     * Relación uno a muchos con la entidad Account.
     * Un banco puede tener muchas cuentas asociadas.
     */
    @OneToMany(mappedBy = "bank", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference("account-bank")
    private Set<Account> accounts = new HashSet<>();

}