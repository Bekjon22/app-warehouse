package uz.pdp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.common.MapstructMapper;
import uz.pdp.domain.Supplier;
import uz.pdp.model.SupplierAddDto;
import uz.pdp.model.SupplierDto;
import uz.pdp.model.response.ApiResponse;
import uz.pdp.repository.SupplierRepo;
import uz.pdp.service.SupplierService;

import java.util.Optional;

import static uz.pdp.model.response.ApiResponse.response;

@Service
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepo supplierRepo;
    private final MapstructMapper mapstructMapper;

    @Autowired
    public SupplierServiceImpl(SupplierRepo supplierRepo, MapstructMapper mapstructMapper) {
        this.supplierRepo = supplierRepo;
        this.mapstructMapper = mapstructMapper;
    }

    @Override
    public ResponseEntity<ApiResponse<Supplier>> save(SupplierAddDto dto) {

        boolean exists = supplierRepo.existsByPhoneNumber(dto.getPhoneNumber());
        if (exists) {
            return response(String.format("This Phone Number  [ %s ] already exist!", dto.getPhoneNumber()), HttpStatus.CONFLICT);
        }
        return response(supplierRepo.save(mapstructMapper.toSupplier(dto)));
    }

    @Override
    public ResponseEntity<ApiResponse<Supplier>> get(Long id) {
        Optional<Supplier> optionalSupplier = supplierRepo.findById(id);
        return optionalSupplier.map(ApiResponse::response).orElseGet(() ->
                response(String.format("This Supplier id, %s not found!", id), HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<ApiResponse<Page<Supplier>>> getList(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Supplier> suppliers = supplierRepo.findAll(pageable);
        if (suppliers.isEmpty()) {
            return response("Suppliers not found", HttpStatus.NOT_FOUND);
        }
        return response(suppliers);
    }

    @Override
    public ResponseEntity<ApiResponse<Supplier>> update(Long id, SupplierDto dto) {
        Optional<Supplier> optionalSupplier = supplierRepo.findById(id);
        if (!optionalSupplier.isPresent()) {
            return response(String.format("This Supplier id, %s not found!", id), HttpStatus.NOT_FOUND);
        }
        boolean phoneNumberAndIdNot = supplierRepo.existsByPhoneNumberAndIdNot(dto.getPhoneNumber(), id);
        if (phoneNumberAndIdNot) {
            return response(String.format("This Phone Number  [ %s ] already exist!", dto.getPhoneNumber()), HttpStatus.CONFLICT);
        }
        Supplier supplier = optionalSupplier.get();
        return response(supplierRepo.save(mapstructMapper.getSupplier(supplier, dto)));
    }

    @Override
    public ResponseEntity<ApiResponse<Boolean>> delete(Long id) {
        Optional<Supplier> optionalSupplier = supplierRepo.findById(id);
        if (!optionalSupplier.isPresent()) {
            return response(String.format("This Supplier id, %s not found!", id), HttpStatus.NOT_FOUND);
        }
        supplierRepo.delete(optionalSupplier.get());
        return response(true);
    }

    @Override
    public Supplier validate(Long id) {
        Optional<Supplier> optionalSupplier = supplierRepo.findById(id);
        if (!optionalSupplier.isPresent()) {
            throw new RuntimeException("Supplier id = " + id + ", not found!");
        }
        Supplier supplier = optionalSupplier.get();
        if (!supplier.isActive()) {
            throw new RuntimeException("Supplier id = " + id + ", is inactive!");
        }
        return supplier;

    }
}
