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


    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> getAllCategories(){
        return categoryRepository
                .findAll()
                .stream()
                .map(categoryMapper::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoryResponseDTO getCategoryById(Long id){
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException(CATEGORY_NOT_FOUND));

        return categoryMapper.toResponseDTO(category);
    }


    @Transactional
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO){
        if(categoryRepository.existsByNameIgnoreCase(categoryRequestDTO.getName())){
            throw new ResourceNotFoundException(CATEGORY_EXIST_NAME);
        }

        Category category = categoryMapper.toEntity(categoryRequestDTO);
        Category createdcategory = categoryRepository.save(category);

        return categoryMapper.toResponseDTO(createdcategory);

    }

    @Transactional
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO){
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(CATEGORY_NOT_FOUND));

        validateUniqueName(existingCategory.getName(), categoryRequestDTO.getName());

        categoryMapper.updatedCategoryFromDTO(categoryRequestDTO, existingCategory);

        Category updatedCategory = categoryRepository.save(existingCategory);

        return categoryMapper.toResponseDTO(updatedCategory);
    }

    @Transactional
    public void deleteCategory(Long id){

        // Comprobar si existe la categoría
        if(!categoryRepository.existsById(id)){
            throw new ResourceNotFoundException(CATEGORY_NOT_FOUND);
        }

        // Verificar si hay empleados asociados a categoría
        if(employeeRepository.existsByCategory_IdCategory(id)){
            throw new EntityInUseException(ENTITY_IN_USE);
        }


        categoryRepository.deleteById(id);
    }

    private void validateUniqueName(String currentName, String newName){
        if(!currentName.equalsIgnoreCase(newName) && categoryRepository.existsByNameIgnoreCase(newName)){
            throw new BadRequestException(CATEGORY_NOT_FOUND);
        }
    }


}
