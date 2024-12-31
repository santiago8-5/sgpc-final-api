package com.grupoingenios.sgpc.sgpc_api_final.repository.employee;

import com.grupoingenios.sgpc.sgpc_api_final.entity.employee.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByNameIgnoreCase(String name);

}

