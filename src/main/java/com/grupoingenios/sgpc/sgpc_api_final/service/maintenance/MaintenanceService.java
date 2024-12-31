package com.grupoingenios.sgpc.sgpc_api_final.service.maintenance;


import com.grupoingenios.sgpc.sgpc_api_final.dto.maintenance.MaintenanceRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.maintenance.MaintenanceResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.employee.Employee;
import com.grupoingenios.sgpc.sgpc_api_final.entity.maintenance.Maintenance;
import com.grupoingenios.sgpc.sgpc_api_final.entity.maintenance.RelatedEntityType;
import com.grupoingenios.sgpc.sgpc_api_final.exception.BadRequestException;
import com.grupoingenios.sgpc.sgpc_api_final.exception.ResourceNotFoundException;
import com.grupoingenios.sgpc.sgpc_api_final.mapper.maintenance.MaintenanceMapper;
import com.grupoingenios.sgpc.sgpc_api_final.repository.employee.EmployeeRepository;
import com.grupoingenios.sgpc.sgpc_api_final.repository.machinery.MachineryRepository;
import com.grupoingenios.sgpc.sgpc_api_final.repository.maintenance.MaintenanceRepository;
import com.grupoingenios.sgpc.sgpc_api_final.repository.vehicle.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.grupoingenios.sgpc.sgpc_api_final.constants.AppConstant.*;

@Service
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final MaintenanceMapper maintenanceMapper;
    private final VehicleRepository vehicleRepository;
    private final MachineryRepository machineryRepository;
    private final EmployeeRepository employeeRepository;

    public MaintenanceService(MaintenanceRepository maintenanceRepository, MaintenanceMapper maintenanceMapper,
                              VehicleRepository vehicleRepository, MachineryRepository machineryRepository, EmployeeRepository employeeRepository) {
        this.maintenanceRepository = maintenanceRepository;
        this.maintenanceMapper = maintenanceMapper;
        this.vehicleRepository = vehicleRepository;
        this.machineryRepository = machineryRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional(readOnly = true)
    public List<MaintenanceResponseDTO> getAllMaintenance(){
        return maintenanceRepository
                .findAll()
                .stream()
                .map(maintenanceMapper::toResponseDto)
                .toList();

    }

    @Transactional
    public MaintenanceResponseDTO createMaintenance(MaintenanceRequestDTO maintenanceRequestDTO){

        Employee employee = getEmployeeById(maintenanceRequestDTO.getEmployeeId());


        if(maintenanceRequestDTO.getRelatedEntityType() == RelatedEntityType.VEHICULO){
            if(!vehicleRepository.existsById(maintenanceRequestDTO.getRelatedEntityId())){
                throw new ResourceNotFoundException(VEHICLE_NOT_FOUND);
            }
        }else if(maintenanceRequestDTO.getRelatedEntityType() == RelatedEntityType.MAQUINARIA){
            if(!machineryRepository.existsById(maintenanceRequestDTO.getRelatedEntityId())){
                throw new ResourceNotFoundException(MACHINARY_NOT_FOUND);
            }
        }

        Maintenance maintenance = maintenanceMapper.toEntity(maintenanceRequestDTO);
        maintenance.setEmployee(employee);

        Maintenance savedMaintenance = maintenanceRepository.save(maintenance);

        return maintenanceMapper.toResponseDto(savedMaintenance);
    }

    @Transactional
    public MaintenanceResponseDTO updateMaintenance(Long id, MaintenanceRequestDTO maintenanceRequestDTO){

        Employee employee = getEmployeeById(maintenanceRequestDTO.getEmployeeId());

        // Validar si la entidad relacionada (vehículo o maquinaria) existe
        switch (maintenanceRequestDTO.getRelatedEntityType()) {
            case VEHICULO -> {
                if (!vehicleRepository.existsById(maintenanceRequestDTO.getRelatedEntityId())) {
                    throw new ResourceNotFoundException(VEHICLE_NOT_FOUND);
                }
            }
            case MAQUINARIA -> {
                if (!machineryRepository.existsById(maintenanceRequestDTO.getRelatedEntityId())) {
                    throw new ResourceNotFoundException(MACHINARY_NOT_FOUND);
                }
            }
            default -> throw new BadRequestException("Tipo de entidad relacionada no soportado.");
        }

        Maintenance existingMaintenance = maintenanceRepository
                .findById(id).orElseThrow(()-> new ResourceNotFoundException("No se encontró el registro"));

        maintenanceMapper.updatedRolFromDTO(maintenanceRequestDTO, existingMaintenance);
        existingMaintenance.setEmployee(employee);

        Maintenance updatedMaintenance = maintenanceRepository.save(existingMaintenance);

        return maintenanceMapper.toResponseDto(updatedMaintenance);
    }

    @Transactional
    public void deleteMaintenance(Long id){

        if(!maintenanceRepository.existsById(id)){
            throw new ResourceNotFoundException("No se encontró el registro");
        }
        maintenanceRepository.deleteById(id);

    }

    private Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EMPLOYEE_NOT_FOUND));
    }


}
