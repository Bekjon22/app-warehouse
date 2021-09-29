package uz.pdp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.domain.Currency;
import uz.pdp.domain.Input;
import uz.pdp.domain.Supplier;
import uz.pdp.domain.Warehouse;
import uz.pdp.helper.Utils;
import uz.pdp.model.InputDto;
import uz.pdp.model.response.ApiResponse;
import uz.pdp.model.response.ApiResponse1;
import uz.pdp.repository.InputRepo;
import uz.pdp.service.CurrencyService;
import uz.pdp.service.InputService;
import uz.pdp.service.SupplierService;
import uz.pdp.service.WarehouseService;

import java.time.LocalDateTime;

import static uz.pdp.model.response.ApiResponse.response;

@Service
public class InputServiceImpl implements InputService {
    private final InputRepo inputRepo;
    private final WarehouseService warehouseService;
    private final SupplierService supplierService;
    private final CurrencyService currencyService;

    @Autowired
    public InputServiceImpl(InputRepo inputRepo, WarehouseService warehouseService, SupplierService supplierService, CurrencyService currencyService) {
        this.inputRepo = inputRepo;
        this.warehouseService = warehouseService;
        this.supplierService = supplierService;
        this.currencyService = currencyService;
    }


    @Override
    public ResponseEntity<ApiResponse<Input>> save(InputDto dto) {


        Warehouse warehouse = warehouseService.validate(dto.getWarehouseId());
        Supplier supplier = supplierService.validate(dto.getSupplierId());
        Currency currency = currencyService.validate(dto.getCurrencyId());

        Input input = new Input();
        input.setCode(Utils.generateCode());
        input.setCurrency(currency);
        input.setDate(LocalDateTime.now());
        input.setFactureNumber(dto.getFactureNumber());
        input.setSupplier(supplier);
        input.setWarehouse(warehouse);

        return response(inputRepo.save(input));

    }
}
