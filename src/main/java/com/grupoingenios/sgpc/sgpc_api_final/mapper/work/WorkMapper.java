package com.grupoingenios.sgpc.sgpc_api_final.mapper.work;

import com.grupoingenios.sgpc.sgpc_api_final.dto.work.WorkRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.work.WorkResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.inventory.Supplier;
import com.grupoingenios.sgpc.sgpc_api_final.entity.work.Work;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.stream.Collectors;

/**
 * Mapper para convertir entre la entidad `Work` y sus respectivos DTOs (`WorkRequestDTO` y `WorkResponseDTO`).
 * Utiliza MapStruct para realizar la conversión de datos entre las entidades y sus representaciones DTO.
 */
@Mapper(componentModel = "spring", imports = {Supplier.class, Collectors.class})
public interface WorkMapper {

    /**
     * Convierte un objeto `WorkRequestDTO` a una entidad `Work`.
     *
     * @param workRequestDTO El objeto DTO `WorkRequestDTO` que se desea mapear.
     * @return La entidad `Work` correspondiente con los datos del DTO.
     */
    Work toEntity(WorkRequestDTO workRequestDTO);

    /**
     * Convierte una entidad `Work` a un objeto `WorkResponseDTO`.
     *
     * @param work La entidad `Work` que se desea mapear.
     * @return Un objeto `WorkResponseDTO` con los datos de la entidad `Work`.
     */
    @Mapping(target = "workTypeName", source = "workType.name")
    @Mapping(target = "supplierNames", expression = "java(work.getSuppliers().stream().map(Supplier::getName).collect(Collectors.toList()))")
    @Mapping(target = "workTypeId", source = "workType.idWorkType")
    WorkResponseDTO toResponseDto(Work work);

    /**
     * Actualiza una entidad `Work` a partir de un objeto `WorkRequestDTO`.
     * Este método se usa para actualizar los atributos de una entidad existente.
     *
     * @param dto El objeto DTO `WorkRequestDTO` con los datos a actualizar.
     * @param entity La entidad `Work` que se va a actualizar.
     */
    @Mapping(target = "idWork", ignore = true)
    void updateWorkFromDTO(WorkRequestDTO dto, @MappingTarget Work entity);

}