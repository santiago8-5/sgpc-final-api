package com.grupoingenios.sgpc.sgpc_api_final.controller.user;

import com.grupoingenios.sgpc.sgpc_api_final.dto.user.UserRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.user.UserResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST para gestionar operaciones relacionadas con usuarios.
 * Proporciona endpoints para realizar operaciones CRUD sobre los usuarios.
 */
@RestController
@RequestMapping("/api/v1/users")
@Validated
public class UserController {
    private final UserService userService;

    /**
     * Constructor para inyectar el servicio de usuarios.
     *
     * @param userService Servicio que contiene la lógica de negocio para los usuarios.
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Obtiene la lista de todos los usuarios.
     *
     * @return Respuesta con la lista de usuarios y un estado HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(){
        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Obtiene los detalles de un usuario por su ID.
     *
     * @param id ID del usuario.
     * @return Respuesta con los detalles del usuario y un estado HTTP 200 (OK).
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id){
        UserResponseDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Crea un nuevo usuario.
     *
     * @param userRequestDTO Datos del usuario a crear.
     * @return Respuesta con los datos del usuario creado y un estado HTTP 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO){
        UserResponseDTO createdUser = userService.createUser(userRequestDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    /**
     * Actualiza un usuario existente por su ID.
     *
     * @param id              ID del usuario a actualizar.
     * @param userRequestDTO  Datos actualizados del usuario.
     * @return Respuesta con los datos del usuario actualizado y un estado HTTP 200 (OK).
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDTO userRequestDTO){
        UserResponseDTO updatedUser = userService.updateUser(id, userRequestDTO);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id ID del usuario a eliminar.
     * @return Respuesta con un estado HTTP 204 (NO CONTENT) si la eliminación fue exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
