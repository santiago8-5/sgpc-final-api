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

/**
 * Servicio encargado de gestionar las operaciones relacionadas con los roles de usuario en el sistema.
 * Proporciona métodos para realizar operaciones CRUD sobre los roles, además de validar reglas de negocio como la unicidad del nombre del rol.
 */
@Service
public class RolService {

    private final RolRepository rolRepository;
    private final RolMapper rolMapper;

    public RolService(RolRepository rolRepository, RolMapper rolMapper) {
        this.rolRepository = rolRepository;
        this.rolMapper = rolMapper;
    }


    /**
     * Obtiene todos los roles en el sistema.
     *
     * @return Lista de roles como DTOs.
     */
    @Transactional(readOnly = true)
    public List<RolResponseDTO> getAllRoles(){
        return rolRepository
                .findAll()
                .stream()
                .map(rolMapper::toResponseDto)
                .toList();
    }


    /**
     * Crea un nuevo rol en el sistema.
     *
     * @param rolRequestDTO DTO con los datos del rol a crear.
     * @return El rol creado como DTO.
     * @throws BadRequestException Si el nombre del rol ya está en uso.
     */
    @Transactional
    public RolResponseDTO createRol(RolRequestDTO rolRequestDTO){

        if(rolRepository.existsByNameIgnoreCase(rolRequestDTO.getName())){
            throw  new BadRequestException(ROLE_EXIST_NAME);
        }

        Rol rol = rolMapper.toEntity(rolRequestDTO);
        Rol savedRol = rolRepository.save(rol);

        return rolMapper.toResponseDto(savedRol);

    }


    /**
     * Actualiza un rol existente en el sistema.
     *
     * @param id ID del rol a actualizar.
     * @param rolRequestDTO DTO con los nuevos datos del rol.
     * @return El rol actualizado como DTO.
     * @throws ResourceNotFoundException Si el rol no existe.
     * @throws BadRequestException Si el nuevo nombre de rol ya está en uso.
     */
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

    /**
     * Elimina un rol del sistema.
     *
     * @param id ID del rol a eliminar.
     * @throws ResourceNotFoundException Si el rol no existe.
     */
    @Transactional
    public void deleteRol(Long id) {
        if(!rolRepository.existsById(id)){
            throw new ResourceNotFoundException(ROLE_NOT_FOUND);
        }
        rolRepository.deleteById(id);
    }

    /**
     * Valida que el nombre del rol no esté ya en uso.
     *
     * @param currentName El nombre actual del rol.
     * @param newName El nuevo nombre del rol.
     * @throws BadRequestException Si el nuevo nombre ya está en uso.
     */
    private void validateUniqueName(String currentName, String newName){
        if(!currentName.equalsIgnoreCase(newName) && rolRepository.existsByNameIgnoreCase(newName)){
            throw new BadRequestException(ROLE_EXIST_NAME);
        }
    }

}
