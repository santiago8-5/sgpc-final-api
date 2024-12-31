package com.grupoingenios.sgpc.sgpc_api_final.dto.machinery;

import com.grupoingenios.sgpc.sgpc_api_final.entity.machinery.StatusTypeMachinery;
import com.grupoingenios.sgpc.sgpc_api_final.entity.machinery.ToolType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class MachineryRequestDTO {

    @NotBlank(message = "EL nombre es obligatorio")
    @Size(max = 100, message = "El nombre no debe de exceder los 100 caracteres")
    private String name;

    @NotBlank(message = "La marca es obligatoria")
    @Size(max = 50, message = "La marca no debe de exceder los 50 caracteres")
    private String brand;

    @NotNull(message = "El modelo es obligatorio")
    private int model;

    @NotBlank(message = "El serial es obligatorio")
    @Size(max = 17, message = "El serial no debe de exceder los 17 caracteres")
    private String serial;

    @NotNull(message = "La fecha de adquisición es obligatoria")
    private LocalDate acquisitionDate;

    @NotNull(message = "El estado es obligatorio")
    private StatusTypeMachinery status;

    @NotNull(message = "El tipo de herramienta es obligatoria")
    private ToolType toolType;
}