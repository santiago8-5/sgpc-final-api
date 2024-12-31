package com.grupoingenios.sgpc.sgpc_api_final.entity.employee;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.math.BigDecimal;

@Entity
@Table(name = "PlantEmployee")
@DiscriminatorValue("PLANTA")
@PrimaryKeyJoinColumn(name = "idEmployee")
@Setter
@Getter
public class PlantEmployee extends Employee{

    @Column(name = "workingHours", nullable = false)
    private Float workingHours;

    @Column(name = "salary", nullable = false, precision = 10, scale = 2)
    private BigDecimal salary;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    @JsonBackReference("department-plant")
    private Department department;

}
