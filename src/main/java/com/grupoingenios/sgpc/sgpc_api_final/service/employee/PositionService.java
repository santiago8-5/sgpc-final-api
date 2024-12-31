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

    @Transactional(readOnly = true)
    public List<PositionResponseDTO> getAllPositions(){
        return positionRepository
                .findAll()
                .stream()
                .map(positionMapper::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public PositionResponseDTO getPositionById(Long id){
        Position position = positionRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(POSITION_NOT_FOUND));

        return positionMapper.toResponseDTO(position);
    }

    @Transactional
    public PositionResponseDTO createPosition(PositionRequestDTO positionRequestDTO){

        if(positionRepository.existsByNameIgnoreCase(positionRequestDTO.getName())){
            throw new BadRequestException(POSITION_EXIST_NAME);
        }

        Position position = positionMapper.toEntity(positionRequestDTO);
        Position savedPosition = positionRepository.save(position);

        return positionMapper.toResponseDTO(savedPosition);

    }

    @Transactional
    public PositionResponseDTO updatePosition(Long id, PositionRequestDTO positionRequestDTO){

        Position existingPosition = positionRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(POSITION_NOT_FOUND));

        validateUniqueName(existingPosition.getName(), positionRequestDTO.getName());

        positionMapper.updatedPositionFromDTO(positionRequestDTO, existingPosition);

        Position updatedPosition = positionRepository.save(existingPosition);

        return positionMapper.toResponseDTO(updatedPosition);

    }


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

    private void validateUniqueName(String currentName, String newName){
        if(!currentName.equalsIgnoreCase(newName) && positionRepository.existsByNameIgnoreCase(newName)){
            throw new BadRequestException(POSITION_EXIST_NAME);
        }
    }


}
