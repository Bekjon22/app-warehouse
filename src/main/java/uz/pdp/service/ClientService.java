package uz.pdp.service;


import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import uz.pdp.domain.Client;
import uz.pdp.model.ClientAddDto;
import uz.pdp.model.ClientDto;
import uz.pdp.model.response.ApiResponse;

public interface ClientService {


    ResponseEntity<ApiResponse<Client>> save(ClientAddDto dto);

    ResponseEntity<ApiResponse<Client>> get(Long id);

    ResponseEntity<ApiResponse<Page<Client>>> getList(int page);

    ResponseEntity<ApiResponse<Client>> update(Long id, ClientDto dto);

    ResponseEntity<ApiResponse<Boolean>> delete(Long id);

    Client validate(Long id);
}
