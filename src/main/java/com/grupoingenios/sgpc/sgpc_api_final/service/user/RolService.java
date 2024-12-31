package com.grupoingenios.sgpc.sgpc_api_final.service.user;


import com.grupoingenios.sgpc.sgpc_api_final.dto.user.RolRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.user.RolResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.user.Rol;
import com.grupoingenios.sgpc.sgpc_api_final.exception.BadRequestException;
import com.grupoingenios.sgpc.sgpc_api_final.exception.ResourceNotFoundException;
import com.grupoingenios.sgpc.sgpc_api_final.mapper.user.RolMapper;
import com.grupoingenios.sgpc.sgpc_api_final.repository.user.RolRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static com.grupoingenios.sgpc.sgpc_api_final.constants.AppConstant.ROLE_EXIST_NAME;
import static com.grupoingenios.sgpc.sgpc_api_final.constants.AppConstant.ROLE_NOT_FOUND;


@Service
public class RolService {

    private final RolRepository rolRepository;
    private final RolMapper rolMapper;

    public RolService(RolRepository rolRepository, RolMapper rolMapper) {
        this.rolRepository = rolRepository;
        this.rolMapper = rolMapper;
    }

    @Transactional(readOnly = true)
    public List<RolResponseDTO> getAllRoles(){
        return rolRepository
                .findAll()
                .stream()
                .map(rolMapper::toResponseDto)
                .toList();
    }

    @Transactional
    public RolResponseDTO createRol(RolRequestDTO rolRequestDTO){

        if(rolRepository.existsByNameIgnoreCase(rolRequestDTO.getName())){
            throw  new BadRequestException(ROLE_EXIST_NAME);
        }

        Rol rol = rolMapper.toEntity(rolRequestDTO);
        Rol savedRol = rolRepository.save(rol);

        return rolMapper.toResponseDto(savedRol);

    }


    @Transactional
    public RolResponseDTO updateRol(Long id, RolRequestDTO rolRequestDTO){

        Rol existingRol = rolRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(ROLE_NOT_FOUND));

        validateUniqueName(existingRol.getName(), rolRequestDTO.getName());

        rolMapper.updatedRolFromDTO(rolRequestDTO, existingRol);

        Rol updatedRol = rolRepository.save(existingRol);

        return rolMapper.toResponseDto(updatedRol);


    }

    @Transactional
    public void deleteRol(Long id) {
        if(!rolRepository.existsById(id)){
            throw new ResourceNotFoundException(ROLE_NOT_FOUND);
        }
        rolRepository.deleteById(id);
    }


    private void validateUniqueName(String currentName, String newName){
        if(!currentName.equalsIgnoreCase(newName) && rolRepository.existsByNameIgnoreCase(newName)){
            throw new BadRequestException(ROLE_EXIST_NAME);
        }
    }

}
