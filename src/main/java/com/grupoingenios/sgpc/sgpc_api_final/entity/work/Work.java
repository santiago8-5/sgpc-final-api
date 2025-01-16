package com.grupoingenios.sgpc.sgpc_api_final.entity.work;

import com.grupoingenios.sgpc.sgpc_api_final.entity.inventory.Supplier;
import com.grupoingenios.sgpc.sgpc_api_final.entity.schedule.Schedule;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "work")
@EntityListeners(AuditingEntityListener.class)
public class Work {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_work")
    private Long idWork;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "description", nullable = false, length = 255)
    private String description;

    @Column(name = "workCode", nullable = false, length = 50)
    private String workCode;

    @Column(name = "estimatedStartDate", nullable = false)
    private LocalDate estimatedStartDate;

    @Column(name = "estimatedEndDate", nullable = false)
    private LocalDate estimatedEndDate;

    private LocalDate actualStartDate;

    private LocalDate actualEndDate;

    @Column(name = "allocatedBudget", nullable = false, precision = 15, scale = 2)
    private BigDecimal allocatedBudget;

    @Column(name = "actualCost", nullable = true, precision = 15, scale = 2)
    private BigDecimal actualCost;

    @Column(name = "address", nullable = false, length = 150)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workType_id", nullable = false)
    private WorkType workType;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "work_supplier",
            joinColumns = @JoinColumn(name = "work_id"),
            inverseJoinColumns = @JoinColumn(name = "supplier_id")
    )
    private Set<Supplier> suppliers = new HashSet<>();


    @Column(name = "latitude", nullable = false, precision = 9, scale = 6)
    private BigDecimal latitude;

    @Column(name = "longitude", nullable = false, precision = 9, scale = 6)
    private BigDecimal longitude;

    @CreatedDate
    private LocalDateTime created_at;

    @LastModifiedDate
    private  LocalDateTime lastModifiedDate;

}

