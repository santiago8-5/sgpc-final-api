package com.grupoingenios.sgpc.sgpc_api_final.repository.maintenance;

import com.grupoingenios.sgpc.sgpc_api_final.entity.maintenance.Maintenance;
import com.grupoingenios.sgpc.sgpc_api_final.entity.maintenance.RelatedEntityType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repositorio para la entidad `Maintenance`.
 * Proporciona métodos para realizar operaciones CRUD sobre los mantenimientos,
 * así como consultas personalizadas para obtener mantenimientos relacionados con una entidad y verificar la existencia de mantenimientos.
 */
public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {

    /**
     * Encuentra todos los mantenimientos relacionados con una entidad específica,
     * especificada por su tipo (`RelatedEntityType`) y el ID de la entidad relacionada.
     *
     * @param type El tipo de la entidad relacionada (por ejemplo, maquinaria o vehículo).
     * @param id El ID de la entidad relacionada.
     * @return Una lista de mantenimientos relacionados con la entidad especificada.
     */
    List<Maintenance> findByRelatedEntityTypeAndRelatedEntityId(RelatedEntityType type, Long id);

    /**
     * Verifica si existen mantenimientos relacionados con una entidad específica,
     * especificada por su tipo (`RelatedEntityType`) y el ID de la entidad relacionada.
     *
     * @param type El tipo de la entidad relacionada.
     * @param id El ID de la entidad relacionada.
     * @return `true` si existen mantenimientos relacionados con la entidad, de lo contrario `false`.
     */
    boolean existsByRelatedEntityTypeAndRelatedEntityId(RelatedEntityType type, Long id);


    /**
     * Verifica si existen mantenimientos asociados a un empleado específico.
     *
     * @param id El ID del empleado.
     * @return `true` si existen mantenimientos asociados al empleado, de lo contrario `false`.
     */
    boolean existsByEmployee_IdEmployee(Long id);


}
