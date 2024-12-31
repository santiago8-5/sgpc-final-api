package com.grupoingenios.sgpc.sgpc_api_final.mapper.work;

import com.grupoingenios.sgpc.sgpc_api_final.dto.work.WorkRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.work.WorkResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.inventory.Supplier;
import com.grupoingenios.sgpc.sgpc_api_final.entity.work.Work;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = {Supplier.class, Collectors.class})
public interface WorkMapper {

    Work toEntity(WorkRequestDTO workRequestDTO);

    @Mapping(target = "workTypeName", source = "workType.name")
    @Mapping(target = "supplierNames", expression = "java(work.getSuppliers().stream().map(Supplier::getName).collect(Collectors.toList()))")
    WorkResponseDTO toResponseDto(Work work);

    @Mapping(target = "idWork", ignore = true)
    void updateWorkFromDTO(WorkRequestDTO dto, @MappingTarget Work entity);

}