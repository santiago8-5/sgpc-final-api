package com.grupoingenios.sgpc.sgpc_api_final.entity.employee;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;

@Entity
@Table(name = "ConstructionWorker")
@DiscriminatorValue("OBRA")
@PrimaryKeyJoinColumn(name = "idEmployee")
@Setter
@Getter
public class ConstructionWorker extends Employee {

    @Column(name = "startDate")
    private LocalDate startDate;

    @Column(name = "endDate")
    private LocalDate endDate;


}
