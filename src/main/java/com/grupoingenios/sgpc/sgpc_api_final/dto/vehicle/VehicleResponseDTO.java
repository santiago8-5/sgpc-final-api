package com.grupoingenios.sgpc.sgpc_api_final.dto.vehicle;

import com.grupoingenios.sgpc.sgpc_api_final.entity.vehicle.StatusTypeVehicle;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * DTO que representa la respuesta con información detallada de un vehículo.
 * Incluye atributos como identificador, nombre, marca, modelo, placas, color, número de serie, estado y marcas de tiempo.
 */
@Setter
@Getter
public class VehicleResponseDTO {

    private Long id_vehicle;

    private String name;

    private String brand;

    private String model;

    private String plates;

    private String color;

    private String serial;

    private StatusTypeVehicle status;

    private LocalDateTime created_at;

    private LocalDateTime lastModifiedDate;

}
