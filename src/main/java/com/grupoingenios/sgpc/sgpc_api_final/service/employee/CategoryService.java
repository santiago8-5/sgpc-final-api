package com.grupoingenios.sgpc.sgpc_api_final.service.employee;

import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.CategoryRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.CategoryResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.employee.Category;
import com.grupoingenios.sgpc.sgpc_api_final.exception.BadRequestException;
import com.grupoingenios.sgpc.sgpc_api_final.exception.EntityInUseException;
import com.grupoingenios.sgpc.sgpc_api_final.exception.ResourceNotFoundException;
import com.grupoingenios.sgpc.sgpc_api_final.mapper.employee.CategoryMapper;
import com.grupoingenios.sgpc.sgpc_api_final.repository.employee.CategoryRepository;
import com.grupoingenios.sgpc.sgpc_api_final.repository.employee.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.grupoingenios.sgpc.sgpc_api_final.constants.AppConstant.*;

/**
 * Servicio que gestiona la lógica de negocio relacionada con las categorías de empleados.
 * Proporciona operaciones CRUD para las categorías, validando reglas de negocio como unicidad del nombre.
 */
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final EmployeeRepository employeeRepository;


    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper, EmployeeRepository employeeRepository){
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.employeeRepository = employeeRepository;
    }


    /**
     * Obtiene todas las categorías de empleados en el sistema.
     *
     * @return Lista de categorías como DTOs.
     */
    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> getAllCategories(){
        return categoryRepository
                .findAll()
                .stream()
                .map(categoryMapper::toResponseDTO)
                .toList();
    }

    /**
     * Obtiene los detalles de una categoría de empleado por su ID.
     *
     * @param id El ID de la categoría a buscar.
     * @return La categoría correspondiente como DTO.
     */
    @Transactional(readOnly = true)
    public CategoryResponseDTO getCategoryById(Long id){
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException(CATEGORY_NOT_FOUND));

        return categoryMapper.toResponseDTO(category);
    }


    /**
     * Crea una nueva categoría de empleado en el sistema.
     *
     * @param categoryRequestDTO DTO con los datos de la categoría a crear.
     * @return La categoría creada como DTO.
     * @throws ResourceNotFoundException Si ya existe una categoría con el mismo nombre.
     */
    @Transactional
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO){
        if(categoryRepository.existsByNameIgnoreCase(categoryRequestDTO.getName())){
            throw new ResourceNotFoundException(CATEGORY_EXIST_NAME);
        }

        Category category = categoryMapper.toEntity(categoryRequestDTO);
        Category createdcategory = categoryRepository.save(category);

        return categoryMapper.toResponseDTO(createdcategory);

    }


    /**
     * Actualiza la información de una categoría existente.
     *
     * @param id El ID de la categoría a actualizar.
     * @param categoryRequestDTO DTO con los nuevos datos de la categoría.
     * @return La categoría actualizada como DTO.
     * @throws ResourceNotFoundException Si la categoría no existe.
     * @throws BadRequestException Si el nuevo nombre de categoría ya está en uso.
     */
    @Transactional
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO){
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(CATEGORY_NOT_FOUND));

        validateUniqueName(existingCategory.getName(), categoryRequestDTO.getName());

        categoryMapper.updatedCategoryFromDTO(categoryRequestDTO, existingCategory);

        Category updatedCategory = categoryRepository.save(existingCategory);

        return categoryMapper.toResponseDTO(updatedCategory);
    }

    /**
     * Elimina una categoría de empleado.
     *
     * @param id El ID de la categoría a eliminar.
     * @throws ResourceNotFoundException Si la categoría no existe.
     * @throws EntityInUseException Si la categoría tiene empleados asociados.
     */
    @Transactional
    public void deleteCategory(Long id){
        if(!categoryRepository.existsById(id)){
            throw new ResourceNotFoundException(CATEGORY_NOT_FOUND);
        }
        if(employeeRepository.existsByCategory_IdCategory(id)){
            throw new EntityInUseException(ENTITY_IN_USE);
        }
        categoryRepository.deleteById(id);
    }

    /**
     * Valida que el nuevo nombre de la categoría no esté en uso.
     *
     * @param currentName El nombre actual de la categoría.
     * @param newName El nuevo nombre de la categoría.
     * @throws BadRequestException Si el nuevo nombre ya está en uso.
     */
    private void validateUniqueName(String currentName, String newName){
        if(!currentName.equalsIgnoreCase(newName) && categoryRepository.existsByNameIgnoreCase(newName)){
            throw new BadRequestException(CATEGORY_NOT_FOUND);
        }
    }


}
