package com.grupoingenios.sgpc.sgpc_api_final.entity.schedule;


import com.grupoingenios.sgpc.sgpc_api_final.entity.employee.Employee;
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
@Table(name = "activityAssignment")
@EntityListeners(AuditingEntityListener.class)
public class ActivityAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long activityAssignmentId;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "responsible", nullable = false)
    private boolean responsible;

    @Enumerated(EnumType.STRING)
    private StatusActivityAssignment status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employeeId", nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "scheduledActivityId", nullable = false)
    private ScheduledActivity scheduledActivity;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

}
