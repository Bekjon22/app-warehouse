package uz.pdp.service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import uz.pdp.domain.Measurement;
import uz.pdp.model.MeasurementAddDto;
import uz.pdp.model.MeasurementDto;
import uz.pdp.model.response.ApiResponse;

public interface MeasurementService {

    ResponseEntity<ApiResponse<Measurement>> save(MeasurementAddDto measurementDto);

    ResponseEntity<ApiResponse<Measurement>> get(Long id);

    ResponseEntity<ApiResponse<Page<Measurement>>> getList(int page);

    ResponseEntity<ApiResponse<Measurement>> update(Long id, MeasurementDto dto);

    ResponseEntity<ApiResponse<Boolean>> delete(Long id);
}
