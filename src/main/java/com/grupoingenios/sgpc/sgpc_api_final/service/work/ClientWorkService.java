package com.grupoingenios.sgpc.sgpc_api_final.service.work;


import com.grupoingenios.sgpc.sgpc_api_final.dto.work.ClientWorkRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.work.ClientWorkResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.work.Client;
import com.grupoingenios.sgpc.sgpc_api_final.entity.work.ClientWork;
import com.grupoingenios.sgpc.sgpc_api_final.entity.work.Work;
import com.grupoingenios.sgpc.sgpc_api_final.exception.EntityInUseException;
import com.grupoingenios.sgpc.sgpc_api_final.exception.ResourceNotFoundException;
import com.grupoingenios.sgpc.sgpc_api_final.mapper.work.ClientWorkMapper;
import com.grupoingenios.sgpc.sgpc_api_final.repository.work.ClientRepository;
import com.grupoingenios.sgpc.sgpc_api_final.repository.work.ClientWorkRepository;
import com.grupoingenios.sgpc.sgpc_api_final.repository.work.WorkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static com.grupoingenios.sgpc.sgpc_api_final.constants.AppConstant.CLIENT_NOT_FOUND;
import static com.grupoingenios.sgpc.sgpc_api_final.constants.AppConstant.WORK_NOT_FOUND;

@Service
public class ClientWorkService {

    private final ClientWorkRepository clientWorkRepository;

    public ClientWorkService(ClientWorkRepository clientWorkRepository) {
        this.clientWorkRepository = clientWorkRepository;

    }

    @Transactional(readOnly = true)
    public List<ClientWorkResponseDTO> getAllClientWork(){
        return clientWorkRepository.findAllClientWorkDTOs();
    }


}
