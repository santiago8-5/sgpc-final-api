package com.grupoingenios.sgpc.sgpc_api_final.service.schedule;

import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ActivityAssignmentResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.mapper.schedule.ActivityAssignmentMapper;
import com.grupoingenios.sgpc.sgpc_api_final.repository.schedule.ActivityAssignmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
public class ActivityAssignmentService {

    private final ActivityAssignmentRepository activityAssignmentRepository;
    private final ActivityAssignmentMapper activityAssignmentMapper;


    public ActivityAssignmentService(ActivityAssignmentRepository activityAssignmentRepository, ActivityAssignmentMapper activityAssignmentMapper) {
        this.activityAssignmentRepository = activityAssignmentRepository;
        this.activityAssignmentMapper = activityAssignmentMapper;

    }

    @Transactional(readOnly = true)
    public List<ActivityAssignmentResponseDTO> getAllActivityAssignment(){
        return activityAssignmentRepository
                .findAll()
                .stream()
                .map(activityAssignmentMapper::toResponseDto)
                .toList();
    }


}
