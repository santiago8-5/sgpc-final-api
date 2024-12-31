package com.grupoingenios.sgpc.sgpc_api_final.controller.inventory;

import com.grupoingenios.sgpc.sgpc_api_final.dto.inventory.SupplierRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.inventory.SupplierResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.service.inventory.SupplierService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/suppliers")
@Validated
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService){
        this.supplierService = supplierService;
    }

    @GetMapping
    public ResponseEntity<List<SupplierResponseDTO>> getAllSuppliers(){
        List<SupplierResponseDTO> suppliers = supplierService.getAllSuppliers();
        return ResponseEntity.ok(suppliers);
    }

    @PostMapping
    public ResponseEntity<SupplierResponseDTO> createSupplier(@Valid @RequestBody SupplierRequestDTO supplierDTO){
        SupplierResponseDTO supplier = supplierService.createSupplier(supplierDTO);
        return new ResponseEntity<>(supplier, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierResponseDTO> updateSupplier(@Valid @PathVariable Long id, @RequestBody SupplierRequestDTO supplierDTO){
        SupplierResponseDTO updatedSupplier = supplierService.updateSupplier(id, supplierDTO);
        return ResponseEntity.ok(updatedSupplier);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id){
        supplierService.deleteSupplier(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
