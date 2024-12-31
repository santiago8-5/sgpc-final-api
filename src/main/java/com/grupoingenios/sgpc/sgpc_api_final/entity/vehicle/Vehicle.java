package com.grupoingenios.sgpc.sgpc_api_final.entity.vehicle;

import com.grupoingenios.sgpc.sgpc_api_final.config.JpaAuditingConfig;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "vehicle")
@EntityListeners(AuditingEntityListener.class)
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_vehicle;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "brand", nullable = false, length = 50)
    private String brand;

    @Column(name = "model", nullable = false, length = 4)
    private String model;

    @Column(name = "plates", nullable = false, length = 8)
    private String plates;

    @Column(name = "color", nullable = false, length = 25)
    private String color;

    @Column(name = "serial", nullable = false, length = 17)
    private String serial;

    @Enumerated(EnumType.STRING)
    private StatusTypeVehicle status;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

}
