package com.grupoingenios.sgpc.sgpc_api_final.mapper.schedule;

import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ScheduledActivityRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ScheduledActivityResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.schedule.ScheduledActivity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ScheduledActivityMapper {

    ScheduledActivity toEntity(ScheduledActivityRequestDTO scheduledActivityRequestDTO);

    @Mapping(target = "nameActivity", source = "activity.name")
    ScheduledActivityResponseDTO toResponseDto(ScheduledActivity scheduledActivity);

    @Mapping(target = "activity", ignore = true)
    @Mapping(target = "schedule", ignore = true)
    void updateScheduledActivityFromDTO(ScheduledActivityRequestDTO dto, @MappingTarget ScheduledActivity entity);

}
