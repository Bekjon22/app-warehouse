package uz.pdp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.common.MapstructMapper;
import uz.pdp.domain.Warehouse;
import uz.pdp.model.WarehouseAddDto;

import uz.pdp.model.WarehouseDto;
import uz.pdp.model.response.ApiResponse;
import uz.pdp.repository.WarehouseRepo;
import uz.pdp.service.WarehouseService;

import java.util.Optional;

import static uz.pdp.model.response.ApiResponse.response;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepo warehouseRepo;
    private final MapstructMapper mapstructMapper;

    @Autowired
    public WarehouseServiceImpl(WarehouseRepo warehouseRepo, MapstructMapper mapstructMapper) {
        this.warehouseRepo = warehouseRepo;
        this.mapstructMapper = mapstructMapper;
    }

    @Override
    public ResponseEntity<ApiResponse<Warehouse>> save(WarehouseAddDto dto) {
        boolean existsByName = warehouseRepo.existsByName(dto.getName());
        if (existsByName){
            return response(String.format("This Warehouse Name [ %s ] already exist!", dto.getName()), HttpStatus.CONFLICT);
        }
        return response(warehouseRepo.save(mapstructMapper.toWarehouse(dto)));
    }

    @Override
    public ResponseEntity<ApiResponse<Warehouse>> get(Long id) {
        Optional<Warehouse> optionalWarehouse = warehouseRepo.findById(id);
        if (!optionalWarehouse.isPresent()){
            return response(String.format("This Warehouse id, %s not found!", id), HttpStatus.NOT_FOUND);
        }
        return response(optionalWarehouse.get());
    }

    @Override
    public ResponseEntity<ApiResponse<Page<Warehouse>>> getList(int page) {
        Pageable pageable = PageRequest.of(page,10);
        Page<Warehouse> warehouses = warehouseRepo.findAll(pageable);
        if (warehouses.isEmpty()) {
            return response("Warehouses not found", HttpStatus.NOT_FOUND);
        }
        return response(warehouses);
    }

    @Override
    public ResponseEntity<ApiResponse<Warehouse>> update(Long id, WarehouseDto dto) {
        Optional<Warehouse> optionalWarehouse = warehouseRepo.findById(id);
        if (!optionalWarehouse.isPresent()) {
            return response(String.format("This Warehouse id, %s not found!", id), HttpStatus.NOT_FOUND);
        }
        Warehouse byNameAndIdNot = warehouseRepo.findByNameAndIdNot(dto.getName(), id);
        if (byNameAndIdNot != null) {
            return response(String.format("This Warehouse name , %s already exist!", dto.getName()), HttpStatus.CONFLICT);
        }
        Warehouse warehouse = optionalWarehouse.get();
        return response(warehouseRepo.save(mapstructMapper.toWarehouse(warehouse,dto)));
    }

    @Override
    public ResponseEntity<ApiResponse<Boolean>> delete(Long id) {
        Optional<Warehouse> optionalWarehouse = warehouseRepo.findById(id);
        if (!optionalWarehouse.isPresent()) {
            return response(String.format("This Warehouse id, %s not found!", id), HttpStatus.NOT_FOUND);
        }
        warehouseRepo.delete(optionalWarehouse.get());
        return response(true);
    }

    @Override
    public Warehouse validate(Long id) {

        Optional<Warehouse> optionalWarehouse = warehouseRepo.findById(id);
        if (!optionalWarehouse.isPresent()) {
            throw new RuntimeException("Warehouse id = " + id + ", not found!");
        }
        Warehouse warehouse = optionalWarehouse.get();
        if (!warehouse.isActive()) {
            throw new RuntimeException("Category id = " + id + ", is inactive!");
        }
        return warehouse;
    }


}
