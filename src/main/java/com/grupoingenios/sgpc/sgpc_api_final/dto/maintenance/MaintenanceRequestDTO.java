package com.grupoingenios.sgpc.sgpc_api_final.dto.maintenance;

import com.grupoingenios.sgpc.sgpc_api_final.entity.maintenance.RelatedEntityType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class MaintenanceRequestDTO {

    @NotNull(message = "Necesitas seleccionar un vehiculo o una maquinaria obligatoriamente")
    private Long relatedEntityId;

    @NotNull(message = "Selecciona de tipo maquina o vehiculo")
    private RelatedEntityType relatedEntityType;

    @NotNull(message = "Selecciona un empleado para el mantenimiento")
    private Long employeeId;

    @NotBlank(message = "El tipo de mantenimiento es obligatorio")
    private String maintenanceType;

    @Size(max = 255, message = "La descripción no debe de exceder los 255 caracteres")
    private String description;

    @Positive(message = "El costo debe ser mayor a 0")
    private Float cost;

    private LocalDate realizationDate;

    private LocalDate nextDate;

}
