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

    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers(){
        return userRepository
                .findAll()
                .stream()
                .map(userMapper::toResponseDto)
                .toList();
    }


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

    @Transactional
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO){

        // Obtener el usuario existente
        User existingUser = userRepository
                .findById(id).orElseThrow(()-> new ResourceNotFoundException(USER_NOT_FOUND));

        // Obtener el rol existente
        Rol rol = getRolById(userRequestDTO.getRolId());

        // Validar el nombre actual es igual al nuevo nombre
        validateUniqueName(existingUser.getUsername(), userRequestDTO.getUsername());

        // Actualizamos el user desde del dto
        userMapper.updateUserFromDTO(userRequestDTO, existingUser);
        existingUser.setRol(rol);

        if (userRequestDTO.getPassword() != null && !userRequestDTO.getPassword().isBlank()) {
            if (!passwordEncoder.matches(userRequestDTO.getPassword(), existingUser.getPassword())) {
                // Codificar y actualizar solo si la contraseña es diferente
                String encodedPassword = passwordEncoder.encode(userRequestDTO.getPassword());
                existingUser.setPassword(encodedPassword);
            }
        }

        // Guardamos los cambios
        User updatedUser = userRepository.save(existingUser);

        // Devolvemos  la respuesta
        return userMapper.toResponseDto(updatedUser);
    }

    @Transactional
    public void deleteUser(Long id){
        if(!userRepository.existsById(id)){
            throw new ResourceNotFoundException(USER_NOT_FOUND);
        }
        userRepository.deleteById(id);
    }

    private void validateUniqueName(String currentName, String newName){
        if(!currentName.equalsIgnoreCase(newName) && userRepository.existsByUsernameIgnoreCase(newName)){
            throw new BadRequestException(USER_EXIST);
        }
    }

    public Rol getRolById(Long id){
        return rolRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(ROLE_NOT_FOUND));
    }

    public UserResponseDTO getUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(USER_NOT_FOUND));
        return userMapper.toResponseDto(user);
    }

}
