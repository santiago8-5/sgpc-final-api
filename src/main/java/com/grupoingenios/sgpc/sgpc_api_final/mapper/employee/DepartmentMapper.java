package com.grupoingenios.sgpc.sgpc_api_final.mapper.employee;

import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.DepartmentRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.DepartmentResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.employee.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para convertir entre la entidad `Department` y los DTOs `DepartmentRequestDTO` y `DepartmentResponseDTO`.
 * Utiliza MapStruct para la conversión de datos entre las entidades y sus representaciones DTO.
 */
@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    /**
     * Mapea un objeto DTO `DepartmentRequestDTO` a una entidad `Department`.
     *
     * @param departmentDTO El objeto DTO `DepartmentRequestDTO` que se desea mapear.
     * @return La entidad `Department` con los datos del DTO.
     */
    Department toEntity(DepartmentRequestDTO departmentDTO);

    /**
     * Mapea una entidad `Department` a un objeto DTO `DepartmentResponseDTO`.
     *
     * @param department La entidad `Department` que se desea mapear.
     * @return Un objeto `DepartmentResponseDTO` con los datos de la entidad `Department`.
     */
    DepartmentResponseDTO toResponseDto(Department department);


    /**
     * Actualiza una entidad `Department` existente con los valores de un objeto DTO `DepartmentRequestDTO`.
     * El campo `idDepartment` es ignorado durante la actualización.
     *
     * @param dto El objeto DTO `DepartmentRequestDTO` con los nuevos datos.
     * @param entity La entidad `Department` que se desea actualizar.
     */
    @Mapping(target = "idDepartment", ignore = true)
    void updatedDepartmentFromDTO(DepartmentRequestDTO dto, @MappingTarget Department entity);
}
