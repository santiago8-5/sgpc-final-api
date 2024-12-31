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

@Setter
@Getter
@Entity
@Table(name = "department")
@EntityListeners(AuditingEntityListener.class)
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long idDepartment;

    @Column(name = "name", nullable = false, length = 80)
    private String name;

    @Column(name = "description", nullable = true, length = 255)
    private String description;

    @Column(name = "email", nullable = true, length = 100)
    private String email;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "department", orphanRemoval = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference("department-plant")
    private List<PlantEmployee> employees = new ArrayList<>();
}
