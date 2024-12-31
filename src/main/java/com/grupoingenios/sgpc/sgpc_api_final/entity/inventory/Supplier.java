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

@Entity
@Setter
@Getter
@Table(name = "supplier")
@EntityListeners(AuditingEntityListener.class)
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_supplier;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "address", length = 100, nullable = false)
    private String address;

    @Column(name = "phone", length = 10, nullable = false)
    private String phone;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Column(name = "rfc", length = 13, nullable = false)
    private String rfc;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "suppliers")
    @JsonBackReference("inventory-supplier")
    private Set<Inventory> inventories = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "suppliers")
    private Set<Work> works = new HashSet<>();

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

}
