package com.grupoingenios.sgpc.sgpc_api_final.dto.vehicle;

import com.grupoingenios.sgpc.sgpc_api_final.entity.vehicle.StatusTypeVehicle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa la solicitud para crear o actualizar un vehículo.
 * Incluye validaciones para garantizar que los datos proporcionados sean consistentes y completos.
 */
@Setter
@Getter
public class VehicleRequestDTO {

    /**
     * Nombre del vehículo.
     * Obligatorio y no debe exceder los 100 caracteres.
     */
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no debe de exceder los 100 caracteres")
    private String name;

    /**
     * Marca del vehículo.
     * Obligatoria y no debe exceder los 50 caracteres.
     */
    @NotBlank(message = "La marca es obligatorio")
    @Size(max = 50, message = "La marca no debe de exceder los 50 caracteres")
    private String brand;

    /**
     * Modelo del vehículo.
     * Obligatorio y no debe exceder los 4 caracteres.
     */
    @NotBlank(message = "El modelo es obligatorio")
    @Size(max = 4, message = "El modelo no debe de exceder los 4 carecteres")
    private String model;

    /**
     * Placas del vehículo.
     * Obligatorias y no deben exceder los 8 caracteres.
     */
    @NotBlank(message = "Las placas son obligatorias")
    @Size(max = 8, message = "Las placas no deben de exceder los 8 caracteres" )
    private String plates;

    /**
     * Color del vehículo.
     * Obligatorio y no debe exceder los 25 caracteres.
     */
    @NotBlank(message = "EL color es obligatorio")
    @Size(max = 25, message = "El color no debe de exceder los 25 caracteres")
    private String color;

    /**
     * Número de serie del vehículo.
     * Obligatorio y no debe exceder los 17 caracteres.
     */
    @NotBlank(message = "El serial es obligatorio")
    @Size(max = 17, message = "El serial no debe de exceder los 17 caracteres")
    private String serial;

    /**
     * Estado actual del vehículo (por ejemplo, ACTIVO, INACTIVO).
     * Obligatorio.
     */
    @NotNull(message = "El status es obligatorio")
    private StatusTypeVehicle status;

}