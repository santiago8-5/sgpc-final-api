package com.grupoingenios.sgpc.sgpc_api_final.service.user;

import com.grupoingenios.sgpc.sgpc_api_final.dto.user.UserRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.user.UserResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.user.Rol;
import com.grupoingenios.sgpc.sgpc_api_final.entity.user.User;
import com.grupoingenios.sgpc.sgpc_api_final.exception.BadRequestException;
import com.grupoingenios.sgpc.sgpc_api_final.exception.ResourceNotFoundException;
import com.grupoingenios.sgpc.sgpc_api_final.mapper.user.UserMapper;
import com.grupoingenios.sgpc.sgpc_api_final.repository.user.RolRepository;
import com.grupoingenios.sgpc.sgpc_api_final.repository.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static com.grupoingenios.sgpc.sgpc_api_final.constants.AppConstant.*;

/**
 * Servicio encargado de gestionar las operaciones relacionadas con los usuarios en el sistema.
 * Proporciona métodos para realizar operaciones CRUD sobre los usuarios, además de validar reglas de negocio como la unicidad del nombre de usuario.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, RolRepository rolRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }



    /**
     * Obtiene todos los usuarios en el sistema.
     *
     * @return Lista de usuarios como DTOs.
     */
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers(){
        return userRepository
                .findAll()
                .stream()
                .map(userMapper::toResponseDto)
                .toList();
    }


    /**
     * Crea un nuevo usuario en el sistema.
     *
     * @param userRequestDTO DTO con los datos del usuario a crear.
     * @return El usuario creado como DTO.
     * @throws BadRequestException Si el nombre de usuario ya está en uso.
     */
    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO){

        if(userRepository.existsByUsernameIgnoreCase(userRequestDTO.getUsername())){
            throw new BadRequestException(USER_EXIST);
        }

        Rol rol = getRolById(userRequestDTO.getRolId());

        String encodedPassword = passwordEncoder.encode(userRequestDTO.getPassword());

        User user = userMapper.toEntity(userRequestDTO);
        user.setRol(rol);
        user.setPassword(encodedPassword);

        User savedUser = userRepository.save(user);

        return userMapper.toResponseDto(savedUser);

    }



    /**
     * Actualiza un usuario existente en el sistema.
     *
     * @param id El ID del usuario a actualizar.
     * @param userRequestDTO DTO con los nuevos datos del usuario.
     * @return El usuario actualizado como DTO.
     * @throws ResourceNotFoundException Si el usuario no existe.
     * @throws BadRequestException Si el nuevo nombre de usuario ya está en uso.
     */
    @Transactional
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO){

        User existingUser = userRepository
                .findById(id).orElseThrow(()-> new ResourceNotFoundException(USER_NOT_FOUND));

        Rol rol = getRolById(userRequestDTO.getRolId());

        validateUniqueName(existingUser.getUsername(), userRequestDTO.getUsername());

        userMapper.updateUserFromDTO(userRequestDTO, existingUser);
        existingUser.setRol(rol);

        if (userRequestDTO.getPassword() != null && !userRequestDTO.getPassword().isBlank()) {
            if (!passwordEncoder.matches(userRequestDTO.getPassword(), existingUser.getPassword())) {
                // Codificar y actualizar solo si la contraseña es diferente
                String encodedPassword = passwordEncoder.encode(userRequestDTO.getPassword());
                existingUser.setPassword(encodedPassword);
            }
        }

        User updatedUser = userRepository.save(existingUser);

        return userMapper.toResponseDto(updatedUser);
    }


    /**
     * Elimina un usuario del sistema.
     *
     * @param id El ID del usuario a eliminar.
     * @throws ResourceNotFoundException Si el usuario no existe.
     */
    @Transactional
    public void deleteUser(Long id){
        if(!userRepository.existsById(id)){
            throw new ResourceNotFoundException(USER_NOT_FOUND);
        }
        userRepository.deleteById(id);
    }


    /**
     * Valida que el nombre de usuario no esté ya en uso.
     *
     * @param currentName El nombre actual del usuario.
     * @param newName El nuevo nombre del usuario.
     * @throws BadRequestException Si el nuevo nombre ya está en uso.
     */
    private void validateUniqueName(String currentName, String newName){
        if(!currentName.equalsIgnoreCase(newName) && userRepository.existsByUsernameIgnoreCase(newName)){
            throw new BadRequestException(USER_EXIST);
        }
    }


    /**
     * Obtiene el rol de usuario por su ID.
     *
     * @param id El ID del rol.
     * @return El rol correspondiente.
     * @throws ResourceNotFoundException Si el rol no existe.
     */
    public Rol getRolById(Long id){
        return rolRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(ROLE_NOT_FOUND));
    }


    /**
     * Obtiene el rol de usuario por su ID.
     *
     * @param id El ID del rol.
     * @return El rol correspondiente.
     * @throws ResourceNotFoundException Si el rol no existe.
     */
    public UserResponseDTO getUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(USER_NOT_FOUND));
        return userMapper.toResponseDto(user);
    }

}
