package uz.pdp.service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import uz.pdp.domain.Supplier;
import uz.pdp.model.SupplierAddDto;
import uz.pdp.model.SupplierDto;
import uz.pdp.model.response.ApiResponse;

public interface SupplierService {

    ResponseEntity<ApiResponse<Supplier>> save(SupplierAddDto dto);

    ResponseEntity<ApiResponse<Supplier>> get(Long id);

    ResponseEntity<ApiResponse<Page<Supplier>>> getList(int page);

    ResponseEntity<ApiResponse<Supplier>> update(Long id, SupplierDto dto);

    ResponseEntity<ApiResponse<Boolean>> delete(Long id);

    Supplier validate(Long id);
}
