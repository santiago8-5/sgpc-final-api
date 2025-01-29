package com.grupoingenios.sgpc.sgpc_api_final.service.vehicle;

import com.grupoingenios.sgpc.sgpc_api_final.dto.vehicle.VehicleRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.vehicle.VehicleResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.maintenance.RelatedEntityType;
import com.grupoingenios.sgpc.sgpc_api_final.entity.vehicle.Vehicle;
import com.grupoingenios.sgpc.sgpc_api_final.exception.BadRequestException;
import com.grupoingenios.sgpc.sgpc_api_final.exception.EntityInUseException;
import com.grupoingenios.sgpc.sgpc_api_final.exception.ResourceNotFoundException;
import com.grupoingenios.sgpc.sgpc_api_final.mapper.vehicle.VehicleMapper;
import com.grupoingenios.sgpc.sgpc_api_final.repository.maintenance.MaintenanceRepository;
import com.grupoingenios.sgpc.sgpc_api_final.repository.vehicle.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.grupoingenios.sgpc.sgpc_api_final.constants.AppConstant.*;

/**
 * Servicio encargado de gestionar las operaciones relacionadas con los vehículos en el sistema.
 * Proporciona métodos para realizar operaciones CRUD sobre los vehículos, además de validar reglas de negocio como la unicidad del nombre del vehículo.
 */
@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;
    private final MaintenanceRepository maintenanceRepository;

    public VehicleService(VehicleRepository vehicleRepository, VehicleMapper vehicleMapper, MaintenanceRepository maintenanceRepository) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper = vehicleMapper;
        this.maintenanceRepository = maintenanceRepository;
    }


    /**
     * Obtiene todos los vehículos en el sistema.
     *
     * @return Lista de vehículos como DTOs.
     */
    @Transactional(readOnly = true)
    public List<VehicleResponseDTO> getAllVehicles(){
        return vehicleRepository
                .findAll()
                .stream()
                .map(vehicleMapper::toResponseDto)
                .toList();
    }


    /**
     * Crea un nuevo vehículo en el sistema.
     *
     * @param vehicleRequestDTO DTO con los datos del vehículo a crear.
     * @return El vehículo creado como DTO.
     * @throws BadRequestException Si el nombre del vehículo ya está en uso.
     */
    @Transactional
    public VehicleResponseDTO createVehicle(VehicleRequestDTO vehicleRequestDTO){

        if(vehicleRepository.existsByNameIgnoreCase(vehicleRequestDTO.getName())){
            throw  new BadRequestException(VEHICLE_EXIST_NAME);
        }

        Vehicle vehicle = vehicleMapper.toEntity(vehicleRequestDTO);
        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        return vehicleMapper.toResponseDto(savedVehicle);
    }


    /**
     * Actualiza un vehículo existente en el sistema.
     *
     * @param id El ID del vehículo a actualizar.
     * @param vehicleRequestDTO DTO con los nuevos datos del vehículo.
     * @return El vehículo actualizado como DTO.
     * @throws ResourceNotFoundException Si el vehículo no existe.
     * @throws BadRequestException Si el nuevo nombre ya está en uso.
     */
    @Transactional
    public VehicleResponseDTO updateVehicle(Long id, VehicleRequestDTO vehicleRequestDTO){

        Vehicle existingVehicle = vehicleRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(VEHICLE_NOT_FOUND));

        validateUniqueName(existingVehicle.getName(), vehicleRequestDTO.getName());

        vehicleMapper.updateVehicleFromDto(vehicleRequestDTO, existingVehicle);

        Vehicle updatedVehicle = vehicleRepository.save(existingVehicle);

        return vehicleMapper.toResponseDto(updatedVehicle);
    }


    /**
     * Elimina un vehículo del sistema.
     *
     * @param id El ID del vehículo a eliminar.
     * @throws ResourceNotFoundException Si el vehículo no existe.
     * @throws EntityInUseException Si el vehículo tiene mantenimientos asociados.
     */
    @Transactional
    public void deleteVehicle(Long id){

        if(!vehicleRepository.existsById(id)){
            throw  new ResourceNotFoundException(VEHICLE_NOT_FOUND);
        }
        if(maintenanceRepository.existsByRelatedEntityTypeAndRelatedEntityId(RelatedEntityType.VEHICULO, id)){
            throw new EntityInUseException(ENTITY_IN_USE);
        }
        vehicleRepository.deleteById(id);
    }


    /**
     * Valida que el nombre del vehículo no esté en uso.
     *
     * @param currentName El nombre actual del vehículo.
     * @param newName El nuevo nombre del vehículo.
     * @throws BadRequestException Si el nuevo nombre ya está en uso.
     */
    private void validateUniqueName(String currentName, String newName){
        if(!currentName.equalsIgnoreCase(newName) && vehicleRepository.existsByNameIgnoreCase(newName)){
            throw new BadRequestException(VEHICLE_EXIST_NAME );
        }
    }

}