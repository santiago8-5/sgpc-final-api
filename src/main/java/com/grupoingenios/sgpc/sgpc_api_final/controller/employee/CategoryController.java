package com.grupoingenios.sgpc.sgpc_api_final.controller.employee;

import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.CategoryRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.CategoryResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.service.employee.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar operaciones relacionadas con categorías.
 * Proporciona endpoints para realizar operaciones CRUD sobre las categorías.
 */
@RestController
@RequestMapping("/api/v1/categories")
@Validated
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Constructor para inyectar el servicio de categorías.
     *
     * @param categoryService Servicio que contiene la lógica de negocio para las categorías.
     */
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    /**
     * Obtiene la lista de todas las categorías.
     *
     * @return Respuesta con la lista de categorías y un estado HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories(){
        List<CategoryResponseDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    /**
     * Obtiene los detalles de una categoría por su ID.
     *
     * @param id ID de la categoría que se desea obtener.
     * @return Respuesta con los detalles de la categoría y un estado HTTP 200 (OK).
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long id){
        CategoryResponseDTO category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    /**
     * Crea una nueva categoría.
     *
     * @param categoryDTO Datos de la categoría a crear.
     * @return Respuesta con los datos de la categoría creada y un estado HTTP 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@Valid @RequestBody CategoryRequestDTO categoryDTO){
        CategoryResponseDTO category = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    /**
     * Actualiza una categoría existente por su ID.
     *
     * @param id          ID de la categoría a actualizar.
     * @param categoryDTO Datos actualizados de la categoría.
     * @return Respuesta con los datos de la categoría actualizada y un estado HTTP 200 (OK).
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryRequestDTO categoryDTO){
        CategoryResponseDTO category = categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok(category);
    }

    /**
     * Elimina una categoría por su ID.
     *
     * @param id ID de la categoría a eliminar.
     * @return Respuesta con un estado HTTP 204 (NO CONTENT) si la eliminación fue exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

