package com.grupoingenios.sgpc.sgpc_api_final.entity.maintenance;

import com.grupoingenios.sgpc.sgpc_api_final.entity.employee.Employee;
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
@Table(name = "maintenance")
@EntityListeners(AuditingEntityListener.class)
public class Maintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMaintenance;

    @Column(name = "relatedEntityId", nullable = false)
    private Long relatedEntityId;

    @Enumerated(EnumType.STRING)
    @Column(name = "relatedEntityType", nullable = false)
    private RelatedEntityType relatedEntityType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "maintenanceType", nullable = false, length = 50)
    private String maintenanceType;

    @Column(name = "description", nullable = true, length = 255)
    private String description;

    @Column(name = "cost", nullable = false)
    private Float cost;

    private LocalDate realizationDate;

    private LocalDate nextDate;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

}
