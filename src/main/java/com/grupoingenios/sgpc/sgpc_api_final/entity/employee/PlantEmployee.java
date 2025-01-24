package com.grupoingenios.sgpc.sgpc_api_final.entity.employee;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

/**
 * Clase que representa a un empleado de tipo "Planta", heredando de la clase `Employee`.
 * Esta clase agrega atributos específicos relacionados con el empleado de planta, como las horas de trabajo,
 * el salario y el departamento al que pertenece.
 */
@Entity
@Table(name = "PlantEmployee")
@DiscriminatorValue("PLANTA")
@PrimaryKeyJoinColumn(name = "idEmployee")
@Setter
@Getter
public class PlantEmployee extends Employee{

    /**
     * Las horas de trabajo asignadas al empleado de planta.
     * Es un valor numérico que indica el número de horas trabajadas.
     */
    @Column(name = "workingHours", nullable = false)
    private Float workingHours;

    /**
     * El salario del empleado de planta.
     * Se usa un tipo `BigDecimal` con precisión 10 y escala 2 para manejar cantidades monetarias de manera precisa.
     */
    @Column(name = "salary", nullable = false, precision = 10, scale = 2)
    private BigDecimal salary;

    /**
     * El departamento al que pertenece el empleado de planta.
     * Cada empleado de planta está asociado a un solo departamento.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    @JsonBackReference("department-plant")
    private Department department;

}
