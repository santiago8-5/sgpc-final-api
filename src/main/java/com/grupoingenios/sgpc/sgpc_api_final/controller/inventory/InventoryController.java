package com.grupoingenios.sgpc.sgpc_api_final.controller.inventory;

import com.grupoingenios.sgpc.sgpc_api_final.dto.inventory.InventoryRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.inventory.InventoryResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.service.inventory.InventoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST para gestionar operaciones relacionadas con inventarios.
 * Proporciona endpoints para realizar operaciones CRUD sobre los inventarios.
 */
@RestController
@RequestMapping("/api/v1/inventories")
@Validated
public class InventoryController {

    private final InventoryService inventoryService;

    /**
     * Constructor para inyectar el servicio de inventarios.
     *
     * @param inventoryService Servicio que contiene la lógica de negocio para los inventarios.
     */
    public InventoryController(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }

    /**
     * Obtiene la lista de todos los inventarios.
     *
     * @return Respuesta con la lista de inventarios y un estado HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<InventoryResponseDTO>> getAllInventory(){
        List<InventoryResponseDTO> inventories = inventoryService.getAllInventories();
        return ResponseEntity.ok(inventories);
    }


    /**
     * Crea un nuevo inventario.
     *
     * @param inventoryRequestDTO Datos del inventario a crear.
     * @return Respuesta con los datos del inventario creado y un estado HTTP 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<InventoryResponseDTO> createInventory(@Valid @RequestBody InventoryRequestDTO inventoryRequestDTO){
        InventoryResponseDTO createdInventory = inventoryService.createInventory(inventoryRequestDTO);
        return new ResponseEntity<>(createdInventory, HttpStatus.CREATED);
    }


    /**
     * Actualiza un inventario existente por su ID.
     *
     * @param id                  ID del inventario a actualizar.
     * @param inventoryRequestDTO Datos actualizados del inventario.
     * @return Respuesta con los datos del inventario actualizado y un estado HTTP 200 (OK).
     */
    @PutMapping("/{id}")
    public ResponseEntity<InventoryResponseDTO> updateInventory(@Valid @PathVariable Long id, @RequestBody InventoryRequestDTO inventoryRequestDTO) {
        InventoryResponseDTO updatedInventory = inventoryService.updateInventory(id, inventoryRequestDTO);
        return ResponseEntity.ok(updatedInventory);
    }


    /**
     * Elimina un inventario por su ID.
     *
     * @param id ID del inventario a eliminar.
     * @return Respuesta con un estado HTTP 204 (NO CONTENT) si la eliminación fue exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id){
        inventoryService.deleteInventoryById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
