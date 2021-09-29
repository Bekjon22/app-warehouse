package uz.pdp.service;


import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import uz.pdp.domain.Warehouse;
import uz.pdp.model.WarehouseAddDto;
import uz.pdp.model.WarehouseDto;
import uz.pdp.model.response.ApiResponse;

public interface WarehouseService {


    ResponseEntity<ApiResponse<Warehouse>> save(WarehouseAddDto dto);

    ResponseEntity<ApiResponse<Warehouse>> get(Long id);

    ResponseEntity<ApiResponse<Page<Warehouse>>> getList(int page);

    ResponseEntity<ApiResponse<Warehouse>> update(Long id, WarehouseDto dto);

    ResponseEntity<ApiResponse<Boolean>> delete(Long id);

    Warehouse validate(Long id);
}
