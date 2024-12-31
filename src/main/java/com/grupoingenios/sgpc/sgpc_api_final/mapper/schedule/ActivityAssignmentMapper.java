package com.grupoingenios.sgpc.sgpc_api_final.mapper.schedule;


import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ActivityAssignmentRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ActivityAssignmentResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.schedule.ActivityAssignment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ActivityAssignmentMapper {

    ActivityAssignment toEntity(ActivityAssignmentRequestDTO activityAssignmentRequestDTO);

    ActivityAssignmentResponseDTO toResponseDto(ActivityAssignment activityAssignment);

    void updateActivityAssignmentFromDTO(ActivityAssignmentRequestDTO dto, @MappingTarget ActivityAssignment entity);

}
