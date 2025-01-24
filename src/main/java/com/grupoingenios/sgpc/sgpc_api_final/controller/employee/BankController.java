package com.grupoingenios.sgpc.sgpc_api_final.controller.employee;

import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.BankRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.BankResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.service.employee.BankService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST para gestionar operaciones relacionadas con bancos.
 * Proporciona endpoints para realizar operaciones CRUD sobre los bancos.
 */
@RestController
@RequestMapping("/api/v1/banks")
@Validated
public class BankController {

    private final BankService bankService;

    /**
     * Constructor para inyectar el servicio de bancos.
     *
     * @param bankService Servicio que contiene la lógica de negocio para los bancos.
     */
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    /**
     * Obtiene la lista de todos los bancos.
     *
     * @return Respuesta con la lista de bancos y un estado HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<BankResponseDTO>> getAllBanks() {
        List<BankResponseDTO> banks = bankService.getAllBanks();
        return ResponseEntity.ok(banks);
    }

    /**
     * Obtiene los detalles de un banco por su ID.
     *
     * @param id ID del banco que se desea obtener.
     * @return Respuesta con los detalles del banco y un estado HTTP 200 (OK).
     */
    @GetMapping("/{id}")
    public ResponseEntity<BankResponseDTO> getBankById(@PathVariable long id) {
        BankResponseDTO bank = bankService.getBankById(id);
        return ResponseEntity.ok(bank);
    }

    /**
     * Crea un nuevo banco.
     *
     * @param bankDTO Datos del banco a crear.
     * @return Respuesta con los datos del banco creado y un estado HTTP 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<BankResponseDTO> createBank(@Valid @RequestBody BankRequestDTO bankDTO) {
        BankResponseDTO createdBank = bankService.createBank(bankDTO);
        return new ResponseEntity<>(createdBank, HttpStatus.CREATED);
    }

    /**
     * Actualiza un banco existente por su ID.
     *
     * @param id      ID del banco a actualizar.
     * @param bankDTO Datos actualizados del banco.
     * @return Respuesta con los datos del banco actualizado y un estado HTTP 200 (OK).
     */
    @PutMapping("/{id}")
    public ResponseEntity<BankResponseDTO> updatedBank(@PathVariable Long id, @Valid @RequestBody BankRequestDTO bankDTO){
        BankResponseDTO updatedBank = bankService.updateBank(id, bankDTO);
        return ResponseEntity.ok(updatedBank);
    }

    /**
     * Elimina un banco por su ID.
     *
     * @param id ID del banco a eliminar.
     * @return Respuesta con un estado HTTP 204 (NO CONTENT) si la eliminación fue exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBank(@PathVariable Long id){
        bankService.deleteBank(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}