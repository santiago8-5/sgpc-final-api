package com.grupoingenios.sgpc.sgpc_api_final.controller.user;

import com.grupoingenios.sgpc.sgpc_api_final.dto.user.RolRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.user.RolResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.service.user.RolService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST para gestionar operaciones relacionadas con roles.
 * Proporciona endpoints para realizar operaciones CRUD sobre roles.
 */
@RestController
@RequestMapping("/api/v1/roles")
@Validated
public class RolController {
    private final RolService rolService;

    /**
     * Constructor para inyectar el servicio de roles.
     *
     * @param rolService Servicio que contiene la lógica de negocio para los roles.
     */
    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    /**
     * Obtiene la lista de todos los roles.
     *
     * @return Respuesta con la lista de roles y un estado HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<RolResponseDTO>> getAllRoles(){
        List<RolResponseDTO> roles = rolService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    /**
     * Crea un nuevo rol.
     *
     * @param rolRequestDTO Datos del rol a crear.
     * @return Respuesta con los datos del rol creado y un estado HTTP 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<RolResponseDTO> createRol(@RequestBody @Valid RolRequestDTO rolRequestDTO){
        RolResponseDTO createdRol = rolService.createRol(rolRequestDTO);
        return new ResponseEntity<>(createdRol, HttpStatus.CREATED);
    }

    /**
     * Actualiza un rol existente por su ID.
     *
     * @param id            ID del rol a actualizar.
     * @param rolRequestDTO Datos actualizados del rol.
     * @return Respuesta con los datos del rol actualizado y un estado HTTP 200 (OK).
     */
    @PutMapping("/{id}")
    public ResponseEntity<RolResponseDTO> updateRol(@PathVariable Long id, @RequestBody @Valid RolRequestDTO rolRequestDTO){
        RolResponseDTO updatedRol = rolService.updateRol(id, rolRequestDTO);
        return  ResponseEntity.ok(updatedRol);
    }

    /**
     * Elimina un rol por su ID.
     *
     * @param id ID del rol a eliminar.
     * @return Respuesta con un estado HTTP 204 (NO CONTENT) si la eliminación fue exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRol(@PathVariable Long id){
        rolService.deleteRol(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
