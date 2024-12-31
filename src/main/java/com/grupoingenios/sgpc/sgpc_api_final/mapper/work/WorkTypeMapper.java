package com.grupoingenios.sgpc.sgpc_api_final.mapper.work;

import com.grupoingenios.sgpc.sgpc_api_final.dto.work.WorkTypeRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.work.WorkTypeResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.work.WorkType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface WorkTypeMapper {


    WorkType toEntity(WorkTypeRequestDTO workTypeRequestDTO);

    WorkTypeResponseDTO toResponseDTO(WorkType workType);

    @Mapping(target = "idWorkType", ignore = true)
    void updatedWorkTypeFromDTO(WorkTypeRequestDTO dto, @MappingTarget WorkType entity);

}
