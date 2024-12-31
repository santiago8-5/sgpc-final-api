package com.grupoingenios.sgpc.sgpc_api_final.mapper.schedule;


import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.StageRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.StageResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.schedule.Stage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StageMapper {

    Stage toEntity(StageRequestDTO activityRequestDTO);

    StageResponseDTO toResponseDto(Stage stage);

    @Mapping(target = "idStage", ignore = true)
    void updateStageFromDTO(StageRequestDTO dto, @MappingTarget Stage entity);

}
