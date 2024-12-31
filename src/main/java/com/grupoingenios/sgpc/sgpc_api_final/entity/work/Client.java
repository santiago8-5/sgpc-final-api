package com.grupoingenios.sgpc.sgpc_api_final.entity.work;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "client")
@EntityListeners(AuditingEntityListener.class)
public class Client {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idClient;

    @Column(name="name", nullable=false, length=100)
    private String name;

    @Column(name="address", nullable=false, length=100)
    private String address;

    @Column(name="municipality", nullable=false, length=70)
    private String municipality;

    @Column(name="state", nullable=false, length=50)
    private String state;

    @Column(name="phone", nullable=false, length=10, unique=true)
    private String phone;

    @Column(name="email", nullable=false, length=100, unique=true)
    private String email;

    @Column(name="rfc", nullable=true, length=13)
    private String rfc;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

}