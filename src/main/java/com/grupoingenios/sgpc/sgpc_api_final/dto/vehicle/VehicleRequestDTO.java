package com.grupoingenios.sgpc.sgpc_api_final.dto.vehicle;

import com.grupoingenios.sgpc.sgpc_api_final.entity.vehicle.StatusTypeVehicle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VehicleRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no debe de exceder los 100 caracteres")
    private String name;

    @NotBlank(message = "La marca es obligatorio")
    @Size(max = 50, message = "La marca no debe de exceder los 50 caracteres")
    private String brand;

    @NotBlank(message = "El modelo es obligatorio")
    @Size(max = 4, message = "El modelo no debe de exceder los 4 carecteres")
    private String model;

    @NotBlank(message = "Las placas son obligatorias")
    @Size(max = 8, message = "Las placas no deben de exceder los 8 caracteres" )
    private String plates;

    @NotBlank(message = "EL color es obligatorio")
    @Size(max = 25, message = "El color no debe de exceder los 25 caracteres")
    private String color;

    @NotBlank(message = "El serial es obligatorio")
    @Size(max = 17, message = "El serial no debe de exceder los 17 caracteres")
    private String serial;

    @NotNull(message = "El status es obligatorio")
    private StatusTypeVehicle status;

}