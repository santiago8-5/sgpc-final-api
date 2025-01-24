package com.grupoingenios.sgpc.sgpc_api_final.entity.employee;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * Clase que representa la clave primaria compuesta para la entidad `Phone`.
 * Esta clave primaria está formada por el número de teléfono y el ID del empleado.
 * Se utiliza para identificar de manera única la relación entre un empleado y su teléfono.
 */
@Embeddable
@Data
@NoArgsConstructor
public class PhoneId implements Serializable {
    /**
     * El número de teléfono asociado a un empleado.
     * Se utiliza como parte de la clave primaria compuesta.
     */
    private String phone;

    /**
     * El ID del empleado al que está asociado el teléfono.
     * Se utiliza como parte de la clave primaria compuesta.
     */
    private Long employeeId;

    /**
     * Constructor que inicializa la clave primaria compuesta con el número de teléfono
     * y el ID del empleado.
     *
     * @param phone El número de teléfono.
     * @param employeeId El ID del empleado al que se asocia el teléfono.
     */
    public PhoneId(String phone, Long employeeId) {
        this.phone = phone;
        this.employeeId = employeeId;
    }
}