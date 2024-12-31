package com.grupoingenios.sgpc.sgpc_api_final.mapper.schedule;

import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ActivityRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ActivityResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.schedule.Activity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ActivityMapper {

    Activity toEntity(ActivityRequestDTO activityRequestDTO);

    @Mapping(target = "stageName", source = "stage.name")
    ActivityResponseDTO toResponseDto(Activity activity);

    @Mapping(target = "idActivity", ignore = true)
    void updateActivityFromDTO(ActivityRequestDTO dto, @MappingTarget Activity entity);

}
