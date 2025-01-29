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

/**
 * Servicio encargado de gestionar las operaciones relacionadas con el mantenimiento de vehículos y maquinaria.
 * Proporciona métodos para realizar operaciones CRUD sobre el mantenimiento,
 * verificando la existencia de la entidad relacionada (vehículo o maquinaria)
 * y la asignación de empleados responsables.
 */
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

    /**
     * Obtiene todos los mantenimientos registrados en el sistema.
     *
     * @return Lista de mantenimientos como DTOs.
     */
    @Transactional(readOnly = true)
    public List<MaintenanceResponseDTO> getAllMaintenance(){
        return maintenanceRepository
                .findAll()
                .stream()
                .map(maintenanceMapper::toResponseDto)
                .toList();

    }

    /**
     * Crea un nuevo mantenimiento en el sistema.
     *
     * @param maintenanceRequestDTO DTO con los datos del mantenimiento a crear.
     * @return El mantenimiento creado como DTO.
     * @throws ResourceNotFoundException Si no se encuentra la entidad relacionada (vehículo o maquinaria).
     */
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


    /**
     * Actualiza un mantenimiento existente en el sistema.
     *
     * @param id El ID del mantenimiento a actualizar.
     * @param maintenanceRequestDTO DTO con los nuevos datos del mantenimiento.
     * @return El mantenimiento actualizado como DTO.
     * @throws ResourceNotFoundException Si no se encuentra el mantenimiento o la entidad relacionada.
     * @throws BadRequestException Si el tipo de entidad relacionada no es válido.
     */
    @Transactional
    public MaintenanceResponseDTO updateMaintenance(Long id, MaintenanceRequestDTO maintenanceRequestDTO){

        Employee employee = getEmployeeById(maintenanceRequestDTO.getEmployeeId());

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


    /**
     * Elimina un mantenimiento del sistema.
     *
     * @param id El ID del mantenimiento a eliminar.
     * @throws ResourceNotFoundException Si no se encuentra el mantenimiento.
     */
    @Transactional
    public void deleteMaintenance(Long id){

        if(!maintenanceRepository.existsById(id)){
            throw new ResourceNotFoundException("No se encontró el registro");
        }
        maintenanceRepository.deleteById(id);

    }


    /**
     * Obtiene un empleado por su ID.
     *
     * @param id El ID del empleado a obtener.
     * @return El empleado correspondiente.
     * @throws ResourceNotFoundException Si no se encuentra el empleado.
     */
    private Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EMPLOYEE_NOT_FOUND));
    }


}
