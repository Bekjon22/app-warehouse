package uz.pdp.service;


import org.springframework.http.ResponseEntity;
import uz.pdp.domain.InputProduct;
import uz.pdp.model.InputProductAddDto;
import uz.pdp.model.response.ApiResponse;
import uz.pdp.model.response.ApiResponse1;
import uz.pdp.model.response.ProductReport;

import java.util.List;

public interface InputProductService {
    ResponseEntity<ApiResponse<InputProduct>> save(InputProductAddDto dto);

    ResponseEntity<ApiResponse<List<InputProduct>>> addAll(List<InputProductAddDto> dto);

    List<ProductReport> get();

    List<ProductReport> get(String date);
}
