package com.grupoingenios.sgpc.sgpc_api_final.entity.schedule;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "schedule")
@EntityListeners(AuditingEntityListener.class)
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSchedule;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", nullable = false, length = 255)
    private String description;

    @Enumerated(EnumType.STRING)
    private StatusSchedule status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "schedule")
    private List<ScheduledActivity> scheduledActivities = new ArrayList<>();

    @CreatedDate
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;



}
