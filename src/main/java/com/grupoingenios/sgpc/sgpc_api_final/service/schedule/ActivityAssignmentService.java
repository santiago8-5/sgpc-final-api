package com.grupoingenios.sgpc.sgpc_api_final.service.schedule;

import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ActivityAssignmentResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.mapper.schedule.ActivityAssignmentMapper;
import com.grupoingenios.sgpc.sgpc_api_final.repository.schedule.ActivityAssignmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Servicio encargado de gestionar las asignaciones de actividades.
 * Proporciona métodos para obtener todas las asignaciones de actividades y realizar operaciones relacionadas.
 */
@Service
public class ActivityAssignmentService {

    private final ActivityAssignmentRepository activityAssignmentRepository;
    private final ActivityAssignmentMapper activityAssignmentMapper;


    /**
     * Constructor que inicializa el servicio con sus dependencias necesarias.
     *
     * @param activityAssignmentRepository El repositorio para las asignaciones de actividades.
     * @param activityAssignmentMapper El mapper para convertir entre entidades y DTOs de asignaciones de actividades.
     */
    public ActivityAssignmentService(ActivityAssignmentRepository activityAssignmentRepository, ActivityAssignmentMapper activityAssignmentMapper) {
        this.activityAssignmentRepository = activityAssignmentRepository;
        this.activityAssignmentMapper = activityAssignmentMapper;

    }

    /**
     * Obtiene todas las asignaciones de actividades registradas en el sistema.
     *
     * @return Lista de asignaciones de actividades como DTOs.
     */
    @Transactional(readOnly = true)
    public List<ActivityAssignmentResponseDTO> getAllActivityAssignment(){
        return activityAssignmentRepository
                .findAll()
                .stream()
                .map(activityAssignmentMapper::toResponseDto)
                .toList();
    }


}
