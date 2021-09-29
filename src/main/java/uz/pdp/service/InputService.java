package uz.pdp.service;

import org.springframework.http.ResponseEntity;
import uz.pdp.domain.Input;
import uz.pdp.model.InputDto;
import uz.pdp.model.response.ApiResponse;
import uz.pdp.model.response.ApiResponse1;

public interface InputService {
    ResponseEntity<ApiResponse<Input>> save(InputDto dto);
}
