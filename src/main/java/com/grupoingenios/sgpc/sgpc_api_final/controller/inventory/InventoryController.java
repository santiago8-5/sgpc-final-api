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

@RestController
@RequestMapping("/api/v1/inventories")
@Validated
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public ResponseEntity<List<InventoryResponseDTO>> getAllInventory(){
        List<InventoryResponseDTO> inventories = inventoryService.getAllInventories();
        return ResponseEntity.ok(inventories);
    }


    @PostMapping
    public ResponseEntity<InventoryResponseDTO> createInventory(@Valid @RequestBody InventoryRequestDTO inventoryRequestDTO){
        InventoryResponseDTO createdInventory = inventoryService.createInventory(inventoryRequestDTO);
        return new ResponseEntity<>(createdInventory, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<InventoryResponseDTO> updateInventory(@Valid @PathVariable Long id, @RequestBody InventoryRequestDTO inventoryRequestDTO) {
        InventoryResponseDTO updatedInventory = inventoryService.updateInventory(id, inventoryRequestDTO);
        return ResponseEntity.ok(updatedInventory);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id){
        inventoryService.deleteInventoryById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
