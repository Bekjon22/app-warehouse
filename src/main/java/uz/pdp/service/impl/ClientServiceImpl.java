package uz.pdp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.common.MapstructMapper;
import uz.pdp.domain.Client;
import uz.pdp.model.ClientAddDto;
import uz.pdp.model.ClientDto;
import uz.pdp.model.response.ApiResponse;
import uz.pdp.repository.ClientRepo;
import uz.pdp.service.ClientService;

import java.util.Optional;

import static uz.pdp.model.response.ApiResponse.response;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepo clientRepo;
    private final MapstructMapper mapstructMapper;

    @Autowired
    public ClientServiceImpl(ClientRepo clientRepo, MapstructMapper mapstructMapper) {
        this.clientRepo = clientRepo;
        this.mapstructMapper = mapstructMapper;
    }

    @Override
    public ResponseEntity<ApiResponse<Client>> save(ClientAddDto dto) {
        boolean exists = clientRepo.existsByPhoneNumber(dto.getPhoneNumber());
        if (exists) {
            return response(String.format("This Phone Number  [ %s ] already exist!", dto.getPhoneNumber()), HttpStatus.CONFLICT);

        }
        return response(clientRepo.save(mapstructMapper.toClient(dto)));
    }

    @Override
    public ResponseEntity<ApiResponse<Client>> get(Long id) {
        Optional<Client> optionalClient = clientRepo.findById(id);
        return optionalClient.map(ApiResponse::response).orElseGet(() ->
                response(String.format("This Client id, %s not found!", id), HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<ApiResponse<Page<Client>>> getList(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Client> clients = clientRepo.findAll(pageable);
        if (clients.isEmpty()) {
            return response("Clients not found", HttpStatus.NOT_FOUND);
        }
        return response(clients);
    }

    @Override
    public ResponseEntity<ApiResponse<Client>> update(Long id, ClientDto dto) {
        Optional<Client> optionalClient = clientRepo.findById(id);
        if (!optionalClient.isPresent()) {
            return response(String.format("This Client id, %s not found!", id), HttpStatus.NOT_FOUND);
        }
        boolean existsByPhoneNumberAndIdNot = clientRepo.existsByPhoneNumberAndIdNot(dto.getPhoneNumber(), id);
        if (existsByPhoneNumberAndIdNot) {
            return response(String.format("This Phone Number  [ %s ] already exist!", dto.getPhoneNumber()), HttpStatus.FORBIDDEN);
        }
        Client client = optionalClient.get();
        return response(clientRepo.save(mapstructMapper.getClient(client, dto)));

    }

    @Override
    public ResponseEntity<ApiResponse<Boolean>> delete(Long id) {
        Optional<Client> optionalClient = clientRepo.findById(id);
        if (!optionalClient.isPresent()) {
            return response(String.format("This Client id, %s not found!", id), HttpStatus.NOT_FOUND);
        }
        clientRepo.delete(optionalClient.get());
        return response(true);
    }

    @Override
    public Client validate(Long id) {
        Optional<Client> optionalClient = clientRepo.findById(id);
        if (!optionalClient.isPresent()) {
            throw new RuntimeException("Client id = " + id + ", not found!");
        }
        Client client = optionalClient.get();
        if (!client.isActive()) {
            throw new RuntimeException("Client id = " + id + ", is inactive!");
        }
        return client;
    }
}
