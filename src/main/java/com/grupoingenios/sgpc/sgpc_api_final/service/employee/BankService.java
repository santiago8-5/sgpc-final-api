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

    @Transactional(readOnly = true)
    public List<BankResponseDTO> getAllBanks() {
        return bankRepository
                .findAll()
                .stream()
                .map(bankMapper::toResponseDto)
                .toList();
    }



    @Transactional(readOnly = true)
    public BankResponseDTO getBankById(Long id){
        Bank bank = bankRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException(BANK_NOT_FOUND));
        return bankMapper.toResponseDto(bank);
    }

    @Transactional
    public BankResponseDTO createBank(BankRequestDTO bankDTO) {
        if(bankRepository.existsByNameIgnoreCase(bankDTO.getName())) {
            throw new ResourceNotFoundException(BANK_EXIST_NAME);
        }
        Bank bank = bankMapper.toEntity(bankDTO);

        Bank savedBank = bankRepository.save(bank);

        return bankMapper.toResponseDto(savedBank);
    }

    @Transactional
    public BankResponseDTO updateBank(Long id, BankRequestDTO bankRequestDTO) {

        Bank existingBank = bankRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(BANK_NOT_FOUND));


        validateUniqueName(existingBank.getName(), bankRequestDTO.getName());

        bankMapper.updatedBankFromDTO(bankRequestDTO, existingBank);

        Bank updatedBank = bankRepository.save(existingBank);

        return bankMapper.toResponseDto(updatedBank);

    }

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


    private void validateUniqueName(String currentName, String newName){
        if(!currentName.equalsIgnoreCase(newName) && bankRepository.existsByNameIgnoreCase(newName)){
            throw new BadRequestException(BANK_EXIST_NAME);
        }
    }
}

