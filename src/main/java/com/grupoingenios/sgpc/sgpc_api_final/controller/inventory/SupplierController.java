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

/**
 * Controlador REST para gestionar operaciones relacionadas con proveedores.
 * Proporciona endpoints para realizar operaciones CRUD sobre los proveedores.
 */
@RestController
@RequestMapping("/api/v1/suppliers")
@Validated
public class SupplierController {

    private final SupplierService supplierService;

    /**
     * Constructor para inyectar el servicio de proveedores.
     *
     * @param supplierService Servicio que contiene la lógica de negocio para los proveedores.
     */
    public SupplierController(SupplierService supplierService){
        this.supplierService = supplierService;
    }

    /**
     * Obtiene la lista de todos los proveedores.
     *
     * @return Respuesta con la lista de proveedores y un estado HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<SupplierResponseDTO>> getAllSuppliers(){
        List<SupplierResponseDTO> suppliers = supplierService.getAllSuppliers();
        return ResponseEntity.ok(suppliers);
    }

    /**
     * Crea un nuevo proveedor.
     *
     * @param supplierDTO Datos del proveedor a crear.
     * @return Respuesta con los datos del proveedor creado y un estado HTTP 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<SupplierResponseDTO> createSupplier(@Valid @RequestBody SupplierRequestDTO supplierDTO){
        SupplierResponseDTO supplier = supplierService.createSupplier(supplierDTO);
        return new ResponseEntity<>(supplier, HttpStatus.CREATED);
    }

    /**
     * Actualiza un proveedor existente por su ID.
     *
     * @param id          ID del proveedor a actualizar.
     * @param supplierDTO Datos actualizados del proveedor.
     * @return Respuesta con los datos del proveedor actualizado y un estado HTTP 200 (OK).
     */
    @PutMapping("/{id}")
    public ResponseEntity<SupplierResponseDTO> updateSupplier(@Valid @PathVariable Long id, @RequestBody SupplierRequestDTO supplierDTO){
        SupplierResponseDTO updatedSupplier = supplierService.updateSupplier(id, supplierDTO);
        return ResponseEntity.ok(updatedSupplier);

    }


    /**
     * Elimina un proveedor por su ID.
     *
     * @param id ID del proveedor a eliminar.
     * @return Respuesta con un estado HTTP 204 (NO CONTENT) si la eliminación fue exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id){
        supplierService.deleteSupplier(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
