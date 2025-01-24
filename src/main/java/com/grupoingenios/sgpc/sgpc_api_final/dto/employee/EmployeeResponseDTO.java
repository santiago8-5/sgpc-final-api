package com.grupoingenios.sgpc.sgpc_api_final.dto.employee;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.Set;

/**
 * DTO que representa la respuesta con información de un empleado.
 * Contiene datos generales del empleado, como su identificador, nombre, RFC,
 * tipo de empleado, posición, categoría, cuentas bancarias y teléfonos asociados.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME, // Usa un campo adicional para identificar la subclase
        include = JsonTypeInfo.As.PROPERTY, // Agrega el tipo como propiedad en el JSON
        property = "employeeType" // Campo discriminador, debe coincidir con los valores de las subclases
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PlantEmployeeResponseDTO.class, name = "PLANTA"),
        @JsonSubTypes.Type(value = ConstructionWorkerResponseDTO.class, name = "OBRA")
})
@Data
@NoArgsConstructor
public class EmployeeResponseDTO {

    /**
     * Identificador único del empleado.
     */
    private Long idEmployee;

    /**
     * Nombre completo del empleado.
     */
    private String name;

    /**
     * RFC del empleado.
     */
    private String rfc;

    /**
     * Correo electrónico del empleado.
     */
    private String email;

    /**
     * Fecha de contratación del empleado.
     */
    private LocalDate hiringDate;

    /**
     * Tipo de empleado (por ejemplo, PLANTA u OBRA).
     */
    private String employeeType;

    /**
     * Nombre del puesto asociado al empleado.
     */
    private String positionName;

    private Long positionId;

    /**
     * Nombre de la categoría asociada al empleado.
     */
    private String categoryName;

    private Long categoryId;

    /**
     * Cuentas bancarias asociadas al empleado.
     */
    private Set<AccountResponseDTO> accounts;

    /**
     * Teléfonos asociados al empleado.
     */
    private Set<PhoneResponseDTO> phones;


    /**
     * Constructor con los atributos mínimos de un empleado.
     *
     * @param idEmployee Identificador único del empleado.
     * @param name       Nombre completo del empleado.
     */
    public EmployeeResponseDTO(Long idEmployee, String name) {
        this.idEmployee = idEmployee;
        this.name = name;
    }
}
