package com.grupoingenios.sgpc.sgpc_api_final.repository.maintenance;

import com.grupoingenios.sgpc.sgpc_api_final.entity.maintenance.Maintenance;
import com.grupoingenios.sgpc.sgpc_api_final.entity.maintenance.RelatedEntityType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {

    List<Maintenance> findByRelatedEntityTypeAndRelatedEntityId(RelatedEntityType type, Long id);
    boolean existsByRelatedEntityTypeAndRelatedEntityId(RelatedEntityType type, Long id);
    // Comprobar si existe un emepleado asignado a un mantenimiento
    boolean existsByEmployee_IdEmployee(Long id);


}
