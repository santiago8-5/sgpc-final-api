package com.grupoingenios.sgpc.sgpc_api_final.service.work;

import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ScheduleRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ScheduleResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ScheduledActivityRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ScheduledActivityResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.work.ClientWorkRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.work.ClientWorkResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.work.WorkRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.work.WorkResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.inventory.Supplier;
import com.grupoingenios.sgpc.sgpc_api_final.entity.schedule.Activity;
import com.grupoingenios.sgpc.sgpc_api_final.entity.schedule.Schedule;
import com.grupoingenios.sgpc.sgpc_api_final.entity.schedule.ScheduledActivity;
import com.grupoingenios.sgpc.sgpc_api_final.entity.work.Client;
import com.grupoingenios.sgpc.sgpc_api_final.entity.work.ClientWork;
import com.grupoingenios.sgpc.sgpc_api_final.entity.work.Work;
import com.grupoingenios.sgpc.sgpc_api_final.entity.work.WorkType;
import com.grupoingenios.sgpc.sgpc_api_final.exception.BadRequestException;
import com.grupoingenios.sgpc.sgpc_api_final.exception.EntityInUseException;
import com.grupoingenios.sgpc.sgpc_api_final.exception.ResourceNotFoundException;
import com.grupoingenios.sgpc.sgpc_api_final.mapper.schedule.ScheduleMapper;
import com.grupoingenios.sgpc.sgpc_api_final.mapper.schedule.ScheduledActivityMapper;
import com.grupoingenios.sgpc.sgpc_api_final.mapper.work.ClientWorkMapper;
import com.grupoingenios.sgpc.sgpc_api_final.mapper.work.WorkMapper;
import com.grupoingenios.sgpc.sgpc_api_final.repository.inventory.SupplierRepository;
import com.grupoingenios.sgpc.sgpc_api_final.repository.schedule.ActivityRepository;
import com.grupoingenios.sgpc.sgpc_api_final.repository.schedule.ScheduleRepository;
import com.grupoingenios.sgpc.sgpc_api_final.repository.schedule.ScheduledActivityRepository;
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

/**
 * Servicio encargado de gestionar las operaciones relacionadas con las obras.
 * Proporciona métodos para realizar operaciones CRUD sobre las obras,
 * asignar clientes, asignar cronogramas y otros procedimientos asociados a las obras.
 */
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
    private final ScheduledActivityRepository scheduledActivityRepository;
    private final ScheduledActivityMapper scheduledActivityMapper;
    private final ActivityRepository activityRepository;


    /**
     * Constructor que inicializa el servicio con los repositorios y mappers necesarios para las operaciones CRUD.
     *
     * @param workRepository Repositorio para gestionar las obras.
     * @param workMapper Mapper para convertir entre entidades y DTOs de obras.
     * @param workTypeRepository Repositorio para gestionar los tipos de obra.
     * @param scheduleRepository Repositorio para gestionar los cronogramas.
     * @param scheduleMapper Mapper para convertir entre entidades y DTOs de cronogramas.
     * @param supplierRepository Repositorio para gestionar los proveedores.
     * @param clientRepository Repositorio para gestionar los clientes.
     * @param clientWorkRepository Repositorio para gestionar las asignaciones de clientes a obras.
     * @param clientWorkMapper Mapper para convertir entre entidades y DTOs de asignaciones de clientes a obras.
     * @param scheduledActivityRepository Repositorio para gestionar las actividades programadas.
     * @param scheduledActivityMapper Mapper para convertir entre entidades y DTOs de actividades programadas.
     * @param activityRepository Repositorio para gestionar las actividades.
     */
    public WorkService(WorkRepository workRepository, WorkMapper workMapper, WorkTypeRepository workTypeRepository,
                       ScheduleRepository scheduleRepository, ScheduleMapper scheduleMapper, SupplierRepository supplierRepository,
                       ClientRepository clientRepository, ClientWorkRepository clientWorkRepository, ClientWorkMapper clientWorkMapper, ScheduledActivityRepository scheduledActivityRepository,
                       ScheduledActivityMapper scheduledActivityMapper, ActivityRepository activityRepository) {
        this.workRepository = workRepository;
        this.workMapper = workMapper;
        this.workTypeRepository = workTypeRepository;
        this.scheduleRepository = scheduleRepository;
        this.scheduleMapper = scheduleMapper;
        this.supplierRepository = supplierRepository;
        this.clientRepository = clientRepository;
        this.clientWorkRepository = clientWorkRepository;
        this.clientWorkMapper= clientWorkMapper;
        this.scheduledActivityRepository = scheduledActivityRepository;
        this.scheduledActivityMapper = scheduledActivityMapper;
        this.activityRepository = activityRepository;
    }

    /**
     * Obtiene todas las asignaciones de clientes a una obra.
     *
     * @param workId El ID de la obra.
     * @return Lista de asignaciones de clientes a la obra como DTOs.
     */
    @Transactional(readOnly = true)
    public List<ClientWorkResponseDTO> getClientWorksByWorkId(Long workId) {
        return workRepository.findAllClientWorkDTOsByWorkId(workId);
    }


    /**
     * Obtiene todas las obras en el sistema.
     *
     * @return Lista de obras como DTOs.
     */
    @Transactional(readOnly = true)
    public List<WorkResponseDTO> getAllWorks(){
        return workRepository
                .findAll()
                .stream()
                .map(workMapper::toResponseDto)
                .toList();
    }



    /**
     * Obtiene los detalles de una obra por su ID.
     *
     * @param id El ID de la obra a buscar.
     * @return El DTO de la obra correspondiente.
     * @throws ResourceNotFoundException Si la obra no existe.
     */
    @Transactional(readOnly = true)
    public WorkResponseDTO getWorkById(Long id) {
        Work work = workRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(WORK_NOT_FOUND));

        WorkResponseDTO responseDTO = workMapper.toResponseDto(work);

        // Verificar si la obra tiene un cronograma asociado
        boolean hasSchedule = workRepository.existsByIdWorkAndScheduleIsNotNull(work.getIdWork());
        responseDTO.setHasSchedule(hasSchedule);

        return responseDTO;
    }


    /**
     * Crea una nueva obra y la guarda en la base de datos.
     *
     * @param workRequestDTO DTO con los datos de la nueva obra.
     * @return El DTO de la obra creada.
     * @throws BadRequestException Si el nombre de la obra ya está en uso.
     */
    @Transactional
    public WorkResponseDTO createWork(WorkRequestDTO workRequestDTO){

        if(workRepository.existsByNameIgnoreCase(workRequestDTO.getName())){
            throw  new BadRequestException(WORK_EXIST_NAME);
        }

        Set<Supplier> suppliers = getSuppliersById(workRequestDTO.getSuppliersId());

        WorkType workType = getWorkTypeId(workRequestDTO.getWorkTypeId());

        Work work = workMapper.toEntity(workRequestDTO);
        work.setWorkCode(generatedCode(work));
        work.setWorkType(workType);

        work.setSuppliers(suppliers);

        Work savedWork = workRepository.save(work);

        return workMapper.toResponseDto(savedWork);

    }


    /**
     * Actualiza una obra existente en el sistema.
     *
     * @param id El ID de la obra a actualizar.
     * @param workRequestDTO DTO con los nuevos datos de la obra.
     * @return El DTO de la obra actualizada.
     * @throws ResourceNotFoundException Si la obra no existe.
     * @throws BadRequestException Si el nombre de la obra ya está en uso.
     */
    @Transactional
    public WorkResponseDTO updateWork(Long id, WorkRequestDTO workRequestDTO) {

        Work existingWork = workRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(WORK_NOT_FOUND));

        validateUniqueName(existingWork.getName(), workRequestDTO.getName());

        WorkType workType = getWorkTypeId(workRequestDTO.getWorkTypeId());

        workMapper.updateWorkFromDTO(workRequestDTO, existingWork);
        existingWork.setWorkType(workType);
        existingWork.setWorkCode(generatedCode(existingWork));

        workRepository.save(existingWork);

        return workMapper.toResponseDto(existingWork);
    }


    /**
     * Crea y asigna un cronograma a una obra.
     *
     * @param idWork El ID de la obra a la que se asignará el cronograma.
     * @param scheduleRequestDTO DTO con los datos del cronograma.
     * @return El DTO del cronograma creado y asignado.
     * @throws BadRequestException Si el cronograma ya existe.
     */
    @Transactional
    public ScheduleResponseDTO createAndAssignSchedule(Long idWork, ScheduleRequestDTO scheduleRequestDTO){

        Work work = existWorkById(idWork);

        if(scheduleRepository.existsByNameIgnoreCase(scheduleRequestDTO.getName())){
            throw new BadRequestException(SCHEDULE_EXIST_NAME);
        }

        Schedule schedule = scheduleMapper.toEntity(scheduleRequestDTO);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        work.setSchedule(savedSchedule);
        workRepository.save(work);

        return scheduleMapper.toResponseDto(savedSchedule);

    }


    /**
     * Elimina una obra del sistema.
     *
     * @param id El ID de la obra a eliminar.
     * @throws ResourceNotFoundException Si la obra no existe.
     */
    @Transactional
    public void deleteWork(Long id){
        if(!workRepository.existsById(id)){
            throw  new ResourceNotFoundException(WORK_NOT_FOUND);
        }
        workRepository.deleteById(id);
    }


    /**
     * Asigna un cliente a una obra.
     * Este método verifica si el cliente y la obra existen y si la relación cliente-obra no está duplicada antes de crearla.
     *
     * @param workId El ID de la obra a la que se asignará el cliente.
     * @param clientWorkRequestDTO El DTO que contiene los datos del cliente y la obra.
     * @return El DTO de la asignación de cliente a obra.
     * @throws EntityInUseException Si la relación cliente-obra ya existe.
     */
    @Transactional
    public ClientWorkResponseDTO assignClientToWork(Long workId,  ClientWorkRequestDTO clientWorkRequestDTO){

        Work work = getWorForClientById(workId);

        Client client = getClientById(clientWorkRequestDTO.getClientId());

        if(clientWorkRepository.existsByClient_IdAndWork_Id(clientWorkRequestDTO.getClientId(), clientWorkRequestDTO.getWorkId())){
            throw new EntityInUseException("El cliente ya está asignado a esta obra.");
        }

        ClientWork clientWork = clientWorkMapper.toEntity(clientWorkRequestDTO);
        clientWork.setWork(work);
        clientWork.setClient(client);

        ClientWork savedClientWork = clientWorkRepository.save(clientWork);

        return clientWorkMapper.toResponseDTO(savedClientWork);
    }


    /**
     * Actualiza la asignación de un cliente a una obra.
     * Este método permite modificar una relación existente entre un cliente y una obra.
     *
     * @param workId El ID de la obra a la que está asignado el cliente.
     * @param clientWorkId El ID de la asignación cliente-obra que se desea actualizar.
     * @param clientWorkRequestDTO El DTO con los nuevos datos para la asignación.
     * @return El DTO actualizado de la asignación de cliente a obra.
     * @throws ResourceNotFoundException Si la relación cliente-obra no existe.
     */
    @Transactional
    public ClientWorkResponseDTO updateClientWork(Long workId, Long clientWorkId, ClientWorkRequestDTO clientWorkRequestDTO){

        ClientWork existingClientWork = clientWorkRepository.findById(clientWorkId)
                .orElseThrow(() -> new ResourceNotFoundException("Relación no encontrada"));

        Client client = getClientById(clientWorkRequestDTO.getClientId());

        clientWorkMapper.updatedClientWorkFromDTO(clientWorkRequestDTO, existingClientWork);
        existingClientWork.setClient(client);

        ClientWork updatedClientWork = clientWorkRepository.save(existingClientWork);

        return clientWorkMapper.toResponseDTO(updatedClientWork);

    }


    /**
     * Elimina una asignación de cliente a una obra.
     * Este método elimina la relación entre un cliente y una obra.
     *
     * @param id El ID de la asignación cliente-obra que se desea eliminar.
     * @throws ResourceNotFoundException Si la relación cliente-obra no existe.
     */
    @Transactional
    public void deleteClientWork(Long id){
        if(!clientWorkRepository.existsById(id)){
            throw new ResourceNotFoundException("Relación no encontrada");
        }
        clientWorkRepository.deleteById(id);
    }

    /**
     * Asigna un proveedor a una obra.
     * Este método verifica si el proveedor ya está asignado a la obra antes de agregarlo.
     *
     * @param workId El ID de la obra a la que se asignará el proveedor.
     * @param supplierId El ID del proveedor a asignar.
     * @throws EntityInUseException Si el proveedor ya está asignado a la obra.
     */
    @Transactional
    public void assignSupplierToWork(Long workId, Long supplierId) {

        Work work = getWorForClientById(workId);

        Supplier supplier = getSupplierById(supplierId);

        if (work.getSuppliers().contains(supplier)) {
            throw new EntityInUseException("El proveedor ya está asignado a esta obra.");
        }

        // Cargar completamente la colección antes de modificarla
        Set<Supplier> suppliers = work.getSuppliers();
        suppliers.add(supplier);

        // Sincronizar la colección
        work.setSuppliers(suppliers);

        workRepository.save(work);
    }


    /**
     * Recupera un proveedor por su ID.
     *
     * @param id El ID del proveedor.
     * @return El proveedor correspondiente al ID.
     * @throws ResourceNotFoundException Si el proveedor no es encontrado.
     */
    private Supplier getSupplierById(Long id){
        return supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SUPPLIER_NOT_FOUND));
    }



    /**
     * Recupera el tipo de trabajo por su ID.
     *
     * @param id El ID del tipo de trabajo.
     * @return El tipo de trabajo correspondiente al ID.
     * @throws ResourceNotFoundException Si el tipo de trabajo no es encontrado.
     */
    private WorkType getWorkTypeId(Long id){
        return workTypeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(WORK_TYPE_NOT_FOUND));
    }


    /**
     * Recupera una obra por su ID.
     *
     * @param id El ID de la obra.
     * @return La obra correspondiente al ID.
     * @throws ResourceNotFoundException Si la obra no es encontrada.
     */
    private Work getWorForClientById(Long id){
        return  workRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(WORK_NOT_FOUND));
    }


    /**
     * Recupera un cliente por su ID.
     *
     * @param id El ID del cliente.
     * @return El cliente correspondiente al ID.
     * @throws ResourceNotFoundException Si el cliente no es encontrado.
     */
    private Client getClientById(Long id){
        return clientRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(CLIENT_NOT_FOUND));
    }


    /**
     * Verifica si una obra existe por su ID.
     *
     * @param idWork El ID de la obra.
     * @return La obra correspondiente al ID.
     * @throws ResourceNotFoundException Si la obra no es encontrada.
     */
    private Work existWorkById(Long idWork){
        return workRepository.findById(idWork)
                .orElseThrow(()-> new ResourceNotFoundException(WORK_NOT_FOUND));
    }


    /**
     * Valida si el nombre de la obra es único.
     *
     * @param currentName El nombre actual de la obra.
     * @param newName El nuevo nombre de la obra.
     * @throws BadRequestException Si el nombre ya está en uso.
     */
    private void validateUniqueName(String currentName, String newName) {
        if (!currentName.equalsIgnoreCase(newName) &&
                workRepository.existsByNameIgnoreCase(newName)) {
            throw new BadRequestException(WORK_EXIST_NAME);
        }
    }


    /**
     * Genera un código único para una obra.
     * El formato es: PROJ-[año]-[ID].
     *
     * @param work La obra para la cual se genera el código.
     * @return El código único generado para la obra.
     */
    public String generatedCode(Work work){
        // Ejemplo de formato: PROJ-[año]-[ID]
        return "OBRA-" + LocalDate.now().getYear() + "-" + (workRepository.count() + 1);
    }


    /**
     * Recupera un conjunto de proveedores a partir de sus IDs.
     *
     * @param suppliersId El conjunto de IDs de los proveedores.
     * @return Un conjunto de proveedores correspondientes a los IDs proporcionados.
     * @throws ResourceNotFoundException Si algún proveedor no es encontrado.
     */
    public Set<Supplier> getSuppliersById(Set<Long> suppliersId) {
        if (suppliersId == null || suppliersId.isEmpty()) {
            return new HashSet<>();
        }
        return suppliersId.stream()
                .map(id -> supplierRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(SUPPLIER_NOT_FOUND)))
                .collect(Collectors.toSet());
    }



    /**
     * Recupera el cronograma asociado a una obra por su ID.
     *
     * @param workId El ID de la obra.
     * @return El cronograma asociado a la obra.
     * @throws ResourceNotFoundException Si no existe un cronograma para la obra.
     */
    @Transactional(readOnly = true)
    public ScheduleResponseDTO getScheduleByWorkId(Long workId) {
        Work work = workRepository.findById(workId)
                .orElseThrow(() -> new ResourceNotFoundException(WORK_NOT_FOUND));

        if (work.getSchedule() == null) {
            throw new ResourceNotFoundException("No hay un cronograma relacionado a esta obra");
        }

        return scheduleMapper.toResponseDto(work.getSchedule());
    }


    /**
     * Recupera todas las actividades programadas de una obra.
     *
     * @param idWork El ID de la obra.
     * @return Una lista de actividades programadas asociadas al cronograma de la obra.
     * @throws ResourceNotFoundException Si no hay un cronograma asociado a la obra.
     */
    @Transactional(readOnly = true)
    public List<ScheduledActivityResponseDTO> getScheduledActivitiesByWorkId(Long idWork) {
        Work work = workRepository.findById(idWork)
                .orElseThrow(() -> new ResourceNotFoundException(WORK_NOT_FOUND));

        if (work.getSchedule() == null) {
            throw new ResourceNotFoundException("No hay un cronograma relacionado a esta obra");
        }

        // Obtener las actividades programadas relacionadas con el cronograma
        List<ScheduledActivity> activities = scheduledActivityRepository.findAllBySchedule_IdSchedule(work.getSchedule().getIdSchedule());

        // Mapear las actividades a DTOs
        return activities.stream()
                .map(scheduledActivityMapper::toResponseDto)
                .toList();
    }



    /**
     * Crea una nueva actividad programada para una obra.
     * Este método valida que la obra tenga un cronograma asociado y que la actividad no esté ya asociada al cronograma.
     *
     * @param idWork El ID de la obra a la que se asignará la actividad.
     * @param scheduledActivityRequestDTO El DTO que contiene los detalles de la actividad programada.
     * @return El DTO de la actividad programada creada.
     * @throws BadRequestException Si el cronograma de la obra no existe o si la actividad ya está asociada.
     */
    @Transactional
    public ScheduledActivityResponseDTO createScheduledActivityFromWork(Long idWork, ScheduledActivityRequestDTO scheduledActivityRequestDTO) {
        Work work = workRepository.findById(idWork)
                .orElseThrow(() -> new ResourceNotFoundException(WORK_NOT_FOUND));

        // Validar que la obra tenga un cronograma asociado
        Schedule schedule = work.getSchedule();
        if (schedule == null) {
            throw new BadRequestException("No hay un cronograma asociado a esta obra. Crea un cronograma primero.");
        }

        // Validar fechas
        validateDates(scheduledActivityRequestDTO);

        Activity activity = getActivityById(scheduledActivityRequestDTO.getIdActivity());

        // Verificar si ya existe la relación entre el cronograma y la actividad
        validateUniqueScheduledActivity(schedule.getIdSchedule(), activity.getIdActivity());

        // Crear y asignar la actividad programada al cronograma
        ScheduledActivity scheduledActivity = scheduledActivityMapper.toEntity(scheduledActivityRequestDTO);
        scheduledActivity.setSchedule(schedule);
        scheduledActivity.setActivity(activity);

        ScheduledActivity savedScheduledActivity = scheduledActivityRepository.save(scheduledActivity);

        return scheduledActivityMapper.toResponseDto(savedScheduledActivity);
    }


    /**
     * Actualiza una actividad programada para una obra.
     *
     * @param idWork El ID de la obra a la que pertenece la actividad.
     * @param idScheduledActivity El ID de la actividad programada a actualizar.
     * @param scheduledActivityRequestDTO El DTO con los nuevos datos de la actividad.
     * @return El DTO actualizado de la actividad programada.
     * @throws ResourceNotFoundException Si la actividad o la obra no son encontrados.
     * @throws BadRequestException Si la actividad no pertenece al cronograma de la obra.
     */
    @Transactional
    public ScheduledActivityResponseDTO updateScheduledActivityFromWork(Long idWork, Long idScheduledActivity, ScheduledActivityRequestDTO scheduledActivityRequestDTO) {
        Work work = workRepository.findById(idWork)
                .orElseThrow(() -> new ResourceNotFoundException(WORK_NOT_FOUND));

        // Verificar si tiene un cronograma asociado
        Schedule schedule = work.getSchedule();
        if (schedule == null) {
            throw new BadRequestException("No hay un cronograma asociado a esta obra.");
        }

        // Validar que la actividad programada existe y pertenece al cronograma
        ScheduledActivity existingScheduledActivity = scheduledActivityRepository.findById(idScheduledActivity)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró este registro"));

        if (!existingScheduledActivity.getSchedule().getIdSchedule().equals(schedule.getIdSchedule())) {
            throw new BadRequestException("La actividad no pertenece al cronograma de esta obra.");
        }

        // Validar la nueva actividad y fechas
        Activity activity = getActivityById(scheduledActivityRequestDTO.getIdActivity());
        validateDates(scheduledActivityRequestDTO);

        // Actualizar los datos de la actividad programada
        scheduledActivityMapper.updateScheduledActivityFromDTO(scheduledActivityRequestDTO, existingScheduledActivity);
        existingScheduledActivity.setActivity(activity);

        ScheduledActivity updatedScheduledActivity = scheduledActivityRepository.save(existingScheduledActivity);

        return scheduledActivityMapper.toResponseDto(updatedScheduledActivity);
    }


    /**
     * Elimina una actividad programada de una obra.
     *
     * @param idWork El ID de la obra a la que pertenece la actividad programada.
     * @param idScheduledActivity El ID de la actividad programada a eliminar.
     * @throws ResourceNotFoundException Si la actividad o la obra no son encontrados.
     * @throws BadRequestException Si la actividad no pertenece al cronograma de la obra.
     */
    @Transactional
    public void deleteScheduledActivityFromWork(Long idWork, Long idScheduledActivity) {
        Work work = workRepository.findById(idWork)
                .orElseThrow(() -> new ResourceNotFoundException(WORK_NOT_FOUND));

        Schedule schedule = work.getSchedule();

        if (schedule == null) {
            throw new BadRequestException("No hay un cronograma asociado a esta obra.");
        }

        // Verificar si la actividad programada pertenece al cronograma de la obra
        ScheduledActivity scheduledActivity = scheduledActivityRepository.findById(idScheduledActivity)
                .orElseThrow(() -> new ResourceNotFoundException("Detalles de actividad programada no encontrados."));

        if (!scheduledActivity.getSchedule().getIdSchedule().equals(schedule.getIdSchedule())) {
            throw new BadRequestException("La actividad no pertenece al cronograma de esta obra.");
        }

        scheduledActivityRepository.delete(scheduledActivity);
    }

    /**
     * Recupera una actividad programada específica de una obra.
     *
     * @param idWork El ID de la obra.
     * @param idScheduledActivity El ID de la actividad programada.
     * @return El DTO de la actividad programada.
     * @throws ResourceNotFoundException Si la actividad no es encontrada.
     */
    @Transactional(readOnly = true)
    public ScheduledActivityResponseDTO getScheduledActivityFromWork(Long idWork, Long idScheduledActivity) {
        ScheduledActivity scheduledActivity = scheduledActivityRepository.findById(idScheduledActivity)
                .orElseThrow(() -> new ResourceNotFoundException("Detalles de actividad programada no encontrados."));

        return scheduledActivityMapper.toResponseDto(scheduledActivity);
    }


    /**
     * Valida que las fechas de inicio y fin de una actividad programada sean correctas.
     *
     * @param dto El DTO que contiene las fechas.
     * @throws BadRequestException Si las fechas no son válidas.
     */
    private void validateDates(ScheduledActivityRequestDTO dto) {
        if (dto.getEstimatedStartDate().isAfter(dto.getEstimatedEndDate())) {
            throw new BadRequestException("La fecha estimada de inicio no puede ser posterior a la fecha estimada de fin.");
        }
        if (dto.getActualStartDate() != null && dto.getActualEndDate() != null && dto.getActualStartDate().isAfter(dto.getActualEndDate())) {
            throw new BadRequestException("La fecha real de inicio no puede ser posterior a la fecha real de fin.");
        }
    }

    /**
     * Recupera una actividad por su ID.
     *
     * @param id El ID de la actividad.
     * @return La actividad correspondiente al ID.
     * @throws ResourceNotFoundException Si la actividad no es encontrada.
     */
    private Activity getActivityById(Long id){
        return activityRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException(ACTIVITY_NOT_FOUND));
    }


    /**
     * Verifica si la actividad ya está asociada al cronograma especificado.
     *
     * @param scheduleId El ID del cronograma.
     * @param activityId El ID de la actividad.
     * @throws BadRequestException Si la actividad ya está asociada al cronograma.
     */
    private void validateUniqueScheduledActivity(Long scheduleId, Long activityId) {
        if (scheduledActivityRepository.existsBySchedule_IdScheduleAndActivity_IdActivity(scheduleId, activityId)) {
            throw new BadRequestException("La actividad ya está asociada al cronograma especificado.");
        }
    }



}