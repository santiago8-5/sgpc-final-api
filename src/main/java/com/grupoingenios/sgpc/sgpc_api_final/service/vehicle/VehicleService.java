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

    @Transactional(readOnly = true)
    public List<VehicleResponseDTO> getAllVehicles(){
        return vehicleRepository
                .findAll()
                .stream()
                .map(vehicleMapper::toResponseDto)
                .toList();
    }


    @Transactional
    public VehicleResponseDTO createVehicle(VehicleRequestDTO vehicleRequestDTO){

        if(vehicleRepository.existsByNameIgnoreCase(vehicleRequestDTO.getName())){
            throw  new BadRequestException(VEHICLE_EXIST_NAME);
        }

        Vehicle vehicle = vehicleMapper.toEntity(vehicleRequestDTO);
        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        return vehicleMapper.toResponseDto(savedVehicle);
    }

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

    private void validateUniqueName(String currentName, String newName){
        if(!currentName.equalsIgnoreCase(newName) && vehicleRepository.existsByNameIgnoreCase(newName)){
            throw new BadRequestException(VEHICLE_EXIST_NAME );
        }
    }

}