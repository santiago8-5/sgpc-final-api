package com.grupoingenios.sgpc.sgpc_api_final.service.work;

import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ScheduleRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ScheduleResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.work.ClientWorkRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.work.ClientWorkResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.work.WorkRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.work.WorkResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.inventory.Supplier;
import com.grupoingenios.sgpc.sgpc_api_final.entity.schedule.Schedule;
import com.grupoingenios.sgpc.sgpc_api_final.entity.work.Client;
import com.grupoingenios.sgpc.sgpc_api_final.entity.work.ClientWork;
import com.grupoingenios.sgpc.sgpc_api_final.entity.work.Work;
import com.grupoingenios.sgpc.sgpc_api_final.entity.work.WorkType;
import com.grupoingenios.sgpc.sgpc_api_final.exception.BadRequestException;
import com.grupoingenios.sgpc.sgpc_api_final.exception.EntityInUseException;
import com.grupoingenios.sgpc.sgpc_api_final.exception.ResourceNotFoundException;
import com.grupoingenios.sgpc.sgpc_api_final.mapper.schedule.ScheduleMapper;
import com.grupoingenios.sgpc.sgpc_api_final.mapper.work.ClientWorkMapper;
import com.grupoingenios.sgpc.sgpc_api_final.mapper.work.WorkMapper;
import com.grupoingenios.sgpc.sgpc_api_final.repository.inventory.SupplierRepository;
import com.grupoingenios.sgpc.sgpc_api_final.repository.schedule.ScheduleRepository;
import com.grupoingenios.sgpc.sgpc_api_final.repository.work.ClientRepository;
import com.grupoingenios.sgpc.sgpc_api_final.repository.work.ClientWorkRepository;
import com.grupoingenios.sgpc.sgpc_api_final.repository.work.WorkRepository;
import com.grupoingenios.sgpc.sgpc_api_final.repository.work.WorkTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static com.grupoingenios.sgpc.sgpc_api_final.constants.AppConstant.*;


@Service
public class WorkService {

    private final WorkRepository workRepository;
    private final WorkMapper workMapper;
    private final WorkTypeRepository workTypeRepository;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final SupplierRepository supplierRepository;
    private final ClientRepository clientRepository;
    private final ClientWorkRepository clientWorkRepository;
    private final ClientWorkMapper clientWorkMapper;


    public WorkService(WorkRepository workRepository, WorkMapper workMapper, WorkTypeRepository workTypeRepository,
                       ScheduleRepository scheduleRepository, ScheduleMapper scheduleMapper, SupplierRepository supplierRepository,
                       ClientRepository clientRepository, ClientWorkRepository clientWorkRepository, ClientWorkMapper clientWorkMapper) {
        this.workRepository = workRepository;
        this.workMapper = workMapper;
        this.workTypeRepository = workTypeRepository;
        this.scheduleRepository = scheduleRepository;
        this.scheduleMapper = scheduleMapper;
        this.supplierRepository = supplierRepository;
        this.clientRepository = clientRepository;
        this.clientWorkRepository = clientWorkRepository;
        this.clientWorkMapper= clientWorkMapper;
    }

    @Transactional(readOnly = true)
    public List<WorkResponseDTO> getAllWorks(){
        return workRepository
                .findAll()
                .stream()
                .map(workMapper::toResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public WorkResponseDTO getWorkById(Long id){
        Work work = workRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(WORK_NOT_FOUND));

        return workMapper.toResponseDto(work);
    }


    @Transactional
    public WorkResponseDTO createWork(WorkRequestDTO workRequestDTO){

        // Validar nombre unico
        if(workRepository.existsByNameIgnoreCase(workRequestDTO.getName())){
            throw  new BadRequestException(WORK_EXIST_NAME);
        }

        // Obtener los proveedores especificados en suppliersId
        Set<Supplier> suppliers = getSuppliersById(workRequestDTO.getSuppliersId());

        // Obtener WorkType por id
        WorkType workType = getWorkTypeId(workRequestDTO.getWorkTypeId());

        Work work = workMapper.toEntity(workRequestDTO);
        work.setWorkCode(generatedCode(work));
        work.setWorkType(workType);

        // Asignar los suppliers al work
        work.setSuppliers(suppliers);

        Work savedWork = workRepository.save(work);

        return workMapper.toResponseDto(savedWork);

    }

    @Transactional
    public WorkResponseDTO updateWork(Long id, WorkRequestDTO workRequestDTO){

        // Obtener la obra existente
        Work existingWork = workRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(WORK_NOT_FOUND));

        // Validamos que el nombre sea unico
        validateUniqueName(existingWork.getName(), workRequestDTO.getName());

        // Obtenemos el tipo de obra
        WorkType workType = getWorkTypeId(workRequestDTO.getWorkTypeId());

        // Obtenemos los nuevos Supplier del request
        Set<Supplier> newSuppliers = getSuppliersById(workRequestDTO.getSuppliersId());


        // Actualizamos la obra con los datos del mapper
        workMapper.updateWorkFromDTO(workRequestDTO, existingWork);
        existingWork.setWorkType(workType);
        existingWork.setWorkCode(generatedCode(existingWork));


        existingWork.setSuppliers(newSuppliers);

        // Guardamos la obra modificada
        Work updatedWork = workRepository.save(existingWork);

        // devolvemos el response
        return workMapper.toResponseDto(updatedWork);

    }

    // CREAR y ASIGNAR SCHEDULE
    @Transactional
    public ScheduleResponseDTO createAndAssignSchedule(Long idWork, ScheduleRequestDTO scheduleRequestDTO){

        // Obtenemos la obra por id
        Work work = existWorkById(idWork);

        // Comprobamos la existencia del registro
        if(scheduleRepository.existsByNameIgnoreCase(scheduleRequestDTO.getName())){
            throw new BadRequestException(SCHEDULE_EXIST_NAME);
        }

        // Crear el cronograma a partir del DTO
        Schedule schedule = scheduleMapper.toEntity(scheduleRequestDTO);

        // Guardar el cronograma
        Schedule savedSchedule = scheduleRepository.save(schedule);

        // Asignar el cronograma a la obra y guardar la obra
        work.setSchedule(savedSchedule);
        workRepository.save(work);

        // Retornamos el DTO de respuesta
        return scheduleMapper.toResponseDto(savedSchedule);

    }

    @Transactional
    public void deleteWork(Long id){
        if(!workRepository.existsById(id)){
            throw  new ResourceNotFoundException(WORK_NOT_FOUND);
        }
        workRepository.deleteById(id);
    }


    // Asignar un cliente una obra
    @Transactional
    public ClientWorkResponseDTO assignClientToWork(Long workId,  ClientWorkRequestDTO clientWorkRequestDTO){

        // Verificar y obtener la existencia de la obra
        Work work = getWorForClientById(workId);

        // Verificar y obtener la existencia del cliente
        Client client = getClientById(clientWorkRequestDTO.getClientId());

        // Verificar si ya existe la relación
        if(clientWorkRepository.existsByClient_IdAndWork_Id(clientWorkRequestDTO.getClientId(), clientWorkRequestDTO.getWorkId())){
            throw new EntityInUseException("El cliente ya está asignado a esta obra.");
        }

        // Convertir el dto a entidad
        ClientWork clientWork = clientWorkMapper.toEntity(clientWorkRequestDTO);
        clientWork.setWork(work);
        clientWork.setClient(client);

        // guardar la entidad
        ClientWork savedClientWork = clientWorkRepository.save(clientWork);

        // Retornamos la respuesta
        return clientWorkMapper.toResponseDTO(savedClientWork);
    }


    // Actualizar un cliente en una obra
    @Transactional
    public ClientWorkResponseDTO updateClientWork(Long workId, Long clientWorkId, ClientWorkRequestDTO clientWorkRequestDTO){

        // Buscar la relación existente
        ClientWork existingClientWork = clientWorkRepository.findById(clientWorkId)
                .orElseThrow(() -> new ResourceNotFoundException("Relación no encontrada"));

        // Verificar y obtener la existencia del cliente
        Client client = getClientById(clientWorkRequestDTO.getClientId());

        // Actualizar
        clientWorkMapper.updatedClientWorkFromDTO(clientWorkRequestDTO, existingClientWork);
        existingClientWork.setClient(client);

        // Guardando los cambios
        ClientWork updatedClientWork = clientWorkRepository.save(existingClientWork);

        return clientWorkMapper.toResponseDTO(updatedClientWork);

    }


    // Eliminar un cliente en una obra
    @Transactional
    public void deleteClientWork(Long id){
        if(!clientWorkRepository.existsById(id)){
            throw new ResourceNotFoundException("Relación no encontrada");
        }
        clientWorkRepository.deleteById(id);
    }


    // Asignar un supplier a una obra
    @Transactional
    public void assignSupplierToWork(Long workId,  Long supplierId){

        // Verificar y obtener la existencia de la obra
        Work work = getWorForClientById(workId);

        // Recuperar el proveedor
        Supplier supplier = getSupplierById(supplierId);

        // Verificar si el proveedor ya está asignado
        if (work.getSuppliers().contains(supplier)) {
            throw new EntityInUseException("El proveedor ya está asignado a esta obra.");
        }

        // Agregar el proveedor a la obra
        work.getSuppliers().add(supplier);

        // Guardar la obra (la relación se guardará automáticamente)
        workRepository.save(work);
    }


    private Supplier getSupplierById(Long id){
        return supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SUPPLIER_NOT_FOUND));
    }







    private WorkType getWorkTypeId(Long id){
        return workTypeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(WORK_TYPE_NOT_FOUND));
    }

    private Work getWorForClientById(Long id){
        return  workRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(WORK_NOT_FOUND));
    }

    private Client getClientById(Long id){
        return clientRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(CLIENT_NOT_FOUND));
    }

    private Work existWorkById(Long idWork){
        return workRepository.findById(idWork)
                .orElseThrow(()-> new ResourceNotFoundException(WORK_NOT_FOUND));
    }

    private void validateUniqueName(String currentName, String newName) {
        if (!currentName.equalsIgnoreCase(newName) &&
                workRepository.existsByNameIgnoreCase(newName)) {
            throw new BadRequestException(WORK_EXIST_NAME);
        }
    }

    public String generatedCode(Work work){
        // Ejemplo de formato: PROJ-[año]-[ID]
        return "OBRA-" + LocalDate.now().getYear() + "-" + (workRepository.count() + 1);
    }

    public Set<Supplier> getSuppliersById(Set<Long> suppliersId) {
        if (suppliersId == null || suppliersId.isEmpty()) {
            return new HashSet<>();
        }
        return suppliersId.stream()
                .map(id -> supplierRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(SUPPLIER_NOT_FOUND)))
                .collect(Collectors.toSet());
    }

}