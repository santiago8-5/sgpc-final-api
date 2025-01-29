package com.grupoingenios.sgpc.sgpc_api_final.service.employee;

import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.PositionRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.PositionResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.employee.Position;
import com.grupoingenios.sgpc.sgpc_api_final.exception.BadRequestException;
import com.grupoingenios.sgpc.sgpc_api_final.exception.EntityInUseException;
import com.grupoingenios.sgpc.sgpc_api_final.exception.ResourceNotFoundException;
import com.grupoingenios.sgpc.sgpc_api_final.mapper.employee.PositionMapper;
import com.grupoingenios.sgpc.sgpc_api_final.repository.employee.EmployeeRepository;
import com.grupoingenios.sgpc.sgpc_api_final.repository.employee.PositionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static com.grupoingenios.sgpc.sgpc_api_final.constants.AppConstant.*;

/**
 * Servicio encargado de gestionar las operaciones relacionadas con los puestos de los empleados.
 * Proporciona métodos para realizar operaciones CRUD sobre los puestos,
 * además de validar reglas de negocio como la unicidad del nombre del puesto.
 */
@Service
public class PositionService {

    private final PositionRepository positionRepository;
    private final PositionMapper positionMapper;
    private final EmployeeRepository employeeRepository;

    public PositionService(PositionRepository positionRepository, PositionMapper positionMapper, EmployeeRepository employeeRepository){
        this.positionRepository = positionRepository;
        this.positionMapper = positionMapper;
        this.employeeRepository = employeeRepository;
    }

    /**
     * Obtiene todos los puestos de empleados en el sistema.
     *
     * @return Lista de puestos como DTOs.
     */
    @Transactional(readOnly = true)
    public List<PositionResponseDTO> getAllPositions(){
        return positionRepository
                .findAll()
                .stream()
                .map(positionMapper::toResponseDTO)
                .toList();
    }

    /**
     * Obtiene los detalles de un puesto por su ID.
     *
     * @param id El ID del puesto a buscar.
     * @return El puesto correspondiente como DTO.
     */
    @Transactional(readOnly = true)
    public PositionResponseDTO getPositionById(Long id){
        Position position = positionRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(POSITION_NOT_FOUND));

        return positionMapper.toResponseDTO(position);
    }

    /**
     * Crea un nuevo puesto de empleado en el sistema.
     *
     * @param positionRequestDTO DTO con los datos del puesto a crear.
     * @return El puesto creado como DTO.
     * @throws BadRequestException Si el nombre del puesto ya está en uso.
     */
    @Transactional
    public PositionResponseDTO createPosition(PositionRequestDTO positionRequestDTO){

        if(positionRepository.existsByNameIgnoreCase(positionRequestDTO.getName())){
            throw new BadRequestException(POSITION_EXIST_NAME);
        }

        Position position = positionMapper.toEntity(positionRequestDTO);
        Position savedPosition = positionRepository.save(position);

        return positionMapper.toResponseDTO(savedPosition);

    }

    /**
     * Actualiza un puesto existente en el sistema.
     *
     * @param id El ID del puesto a actualizar.
     * @param positionRequestDTO DTO con los nuevos datos del puesto.
     * @return El puesto actualizado como DTO.
     * @throws ResourceNotFoundException Si el puesto no existe.
     * @throws BadRequestException Si el nuevo nombre ya está en uso.
     */
    @Transactional
    public PositionResponseDTO updatePosition(Long id, PositionRequestDTO positionRequestDTO){

        Position existingPosition = positionRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(POSITION_NOT_FOUND));

        validateUniqueName(existingPosition.getName(), positionRequestDTO.getName());

        positionMapper.updatedPositionFromDTO(positionRequestDTO, existingPosition);

        Position updatedPosition = positionRepository.save(existingPosition);

        return positionMapper.toResponseDTO(updatedPosition);

    }

    /**
     * Elimina un puesto del sistema.
     *
     * @param id El ID del puesto a eliminar.
     * @throws ResourceNotFoundException Si el puesto no existe.
     * @throws EntityInUseException Si el puesto tiene empleados asociados.
     */
    @Transactional
    public void deletePosition(Long id){
        if(!positionRepository.existsById(id)){
            throw new ResourceNotFoundException(POSITION_NOT_FOUND);
        }

        // Verificar si hay empleados asociados a categoría
        if(employeeRepository.existsByPosition_IdPosition(id)){
            throw new EntityInUseException(ENTITY_IN_USE);
        }

        positionRepository.deleteById(id);
    }

    /**
     * Valida que el nuevo nombre del puesto no esté en uso.
     *
     * @param currentName El nombre actual del puesto.
     * @param newName El nuevo nombre del puesto.
     * @throws BadRequestException Si el nuevo nombre ya está en uso.
     */
    private void validateUniqueName(String currentName, String newName){
        if(!currentName.equalsIgnoreCase(newName) && positionRepository.existsByNameIgnoreCase(newName)){
            throw new BadRequestException(POSITION_EXIST_NAME);
        }
    }


}
