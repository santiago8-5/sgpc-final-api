package com.grupoingenios.sgpc.sgpc_api_final.entity.machinery;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "machinery")
@EntityListeners(AuditingEntityListener.class)
public class Machinery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_machinery;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "brand", nullable = false, length = 50)
    private String brand;

    @Column(name = "model", nullable = false, length = 4)
    private int model;

    @Column(name = "serial", nullable = false, length = 17)
    private String serial;

    @Column(name = "acquisitionDate")
    private LocalDate acquisitionDate;

    @Enumerated(EnumType.STRING)
    private StatusTypeMachinery status;

    @Enumerated(EnumType.STRING)
    private ToolType toolType;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;


}