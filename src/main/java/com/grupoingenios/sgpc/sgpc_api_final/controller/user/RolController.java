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

@RestController
@RequestMapping("/api/v1/roles")
@Validated
public class RolController {
    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping
    public ResponseEntity<List<RolResponseDTO>> getAllRoles(){
        List<RolResponseDTO> roles = rolService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @PostMapping
    public ResponseEntity<RolResponseDTO> createRol(@RequestBody @Valid RolRequestDTO rolRequestDTO){
        RolResponseDTO createdRol = rolService.createRol(rolRequestDTO);
        return new ResponseEntity<>(createdRol, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolResponseDTO> updateRol(@PathVariable Long id, @RequestBody @Valid RolRequestDTO rolRequestDTO){
        RolResponseDTO updatedRol = rolService.updateRol(id, rolRequestDTO);
        return  ResponseEntity.ok(updatedRol);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRol(@PathVariable Long id){
        rolService.deleteRol(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
