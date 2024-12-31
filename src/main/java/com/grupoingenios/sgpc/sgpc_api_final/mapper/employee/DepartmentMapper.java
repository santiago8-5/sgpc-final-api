package com.grupoingenios.sgpc.sgpc_api_final.mapper.employee;

import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.DepartmentRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.DepartmentResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.employee.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    Department toEntity(DepartmentRequestDTO departmentDTO);

    DepartmentResponseDTO toResponseDto(Department department);

    @Mapping(target = "idDepartment", ignore = true)
    void updatedDepartmentFromDTO(DepartmentRequestDTO dto, @MappingTarget Department entity);
}
