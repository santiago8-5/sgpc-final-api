package com.grupoingenios.sgpc.sgpc_api_final.repository.vehicle;

import com.grupoingenios.sgpc.sgpc_api_final.entity.vehicle.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    boolean existsByNameIgnoreCase(String name);
}