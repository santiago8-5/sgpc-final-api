package com.grupoingenios.sgpc.sgpc_api_final.entity.employee;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

/**
 * Representa un trabajador de la construcción, subclase de la entidad Employee.
 * Esta clase contiene información adicional sobre la fecha de inicio y finalización de trabajo de un trabajador de la construcción.
 */
@Entity
@Table(name = "ConstructionWorker")
@DiscriminatorValue("OBRA")
@PrimaryKeyJoinColumn(name = "idEmployee")
@Setter
@Getter
public class ConstructionWorker extends Employee {

    /**
     * Fecha de inicio del trabajo del trabajador de la construcción.
     */
    @Column(name = "startDate")
    private LocalDate startDate;

    /**
     * Fecha de finalización del trabajo del trabajador de la construcción.
     */
    @Column(name = "endDate")
    private LocalDate endDate;


}
