package com.grupoingenios.sgpc.sgpc_api_final.service.employee;

import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.BankRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.BankResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.employee.Bank;
import com.grupoingenios.sgpc.sgpc_api_final.exception.BadRequestException;
import com.grupoingenios.sgpc.sgpc_api_final.exception.EntityInUseException;
import com.grupoingenios.sgpc.sgpc_api_final.exception.ResourceNotFoundException;
import com.grupoingenios.sgpc.sgpc_api_final.mapper.employee.BankMapper;
import com.grupoingenios.sgpc.sgpc_api_final.repository.employee.AccountRepository;
import com.grupoingenios.sgpc.sgpc_api_final.repository.employee.BankRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.grupoingenios.sgpc.sgpc_api_final.constants.AppConstant.*;

/**
 * Servicio que gestiona la lógica de negocio relacionada con los bancos.
 * Proporciona operaciones CRUD para los bancos, validando reglas de negocio como unicidad del nombre.
 */
@Service
public class BankService {

    private final BankRepository bankRepository;
    private final BankMapper bankMapper;
    private final AccountRepository accountRepository;

    public BankService(BankRepository bankRepository, BankMapper bankMapper, AccountRepository accountRepository) {
        this.bankRepository = bankRepository;
        this.bankMapper = bankMapper;
        this.accountRepository = accountRepository;
    }

    /**
     * Obtiene una lista de todos los bancos en el sistema.
     *
     * @return Lista de bancos como DTOs.
     */
    @Transactional(readOnly = true)
    public List<BankResponseDTO> getAllBanks() {
        return bankRepository
                .findAll()
                .stream()
                .map(bankMapper::toResponseDto)
                .toList();
    }


    /**
     * Obtiene los detalles de un banco por su ID.
     *
     * @param id El ID del banco a buscar.
     * @return El banco correspondiente como DTO.
     */
    @Transactional(readOnly = true)
    public BankResponseDTO getBankById(Long id){
        Bank bank = bankRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException(BANK_NOT_FOUND));
        return bankMapper.toResponseDto(bank);
    }

    /**
     * Crea un nuevo banco en el sistema.
     *
     * @param bankDTO DTO con los datos del banco a crear.
     * @return El banco creado como DTO.
     * @throws ResourceNotFoundException Si ya existe un banco con el mismo nombre.
     */
    @Transactional
    public BankResponseDTO createBank(BankRequestDTO bankDTO) {
        if(bankRepository.existsByNameIgnoreCase(bankDTO.getName())) {
            throw new ResourceNotFoundException(BANK_EXIST_NAME);
        }
        Bank bank = bankMapper.toEntity(bankDTO);

        Bank savedBank = bankRepository.save(bank);

        return bankMapper.toResponseDto(savedBank);
    }

    /**
     * Actualiza la información de un banco existente.
     *
     * @param id El ID del banco a actualizar.
     * @param bankRequestDTO DTO con los nuevos datos del banco.
     * @return El banco actualizado como DTO.
     * @throws ResourceNotFoundException Si el banco no existe.
     * @throws BadRequestException Si el nuevo nombre de banco ya está en uso.
     */
    @Transactional
    public BankResponseDTO updateBank(Long id, BankRequestDTO bankRequestDTO) {

        Bank existingBank = bankRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(BANK_NOT_FOUND));


        validateUniqueName(existingBank.getName(), bankRequestDTO.getName());

        bankMapper.updatedBankFromDTO(bankRequestDTO, existingBank);

        Bank updatedBank = bankRepository.save(existingBank);

        return bankMapper.toResponseDto(updatedBank);

    }

    /**
     * Elimina un banco del sistema.
     *
     * @param id El ID del banco a eliminar.
     * @throws ResourceNotFoundException Si el banco no existe.
     * @throws EntityInUseException Si el banco tiene cuentas asociadas.
     */
    @Transactional
    public void deleteBank(Long id) {
        if(!bankRepository.existsById(id)) {
            throw new ResourceNotFoundException(BANK_NOT_FOUND);
        }

        if(accountRepository.existsByBank_IdBank(id)){
            throw new EntityInUseException(ENTITY_IN_USE);
        }

        bankRepository.deleteById(id);
    }


    /**
     * Valida que el nuevo nombre del banco sea único.
     *
     * @param currentName El nombre actual del banco.
     * @param newName El nuevo nombre del banco.
     * @throws BadRequestException Si el nuevo nombre ya está en uso.
     */
    private void validateUniqueName(String currentName, String newName){
        if(!currentName.equalsIgnoreCase(newName) && bankRepository.existsByNameIgnoreCase(newName)){
            throw new BadRequestException(BANK_EXIST_NAME);
        }
    }
}

