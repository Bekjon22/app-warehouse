package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.domain.Warehouse;
import uz.pdp.model.WarehouseAddDto;
import uz.pdp.model.WarehouseDto;
import uz.pdp.model.response.ApiResponse;
import uz.pdp.service.WarehouseService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/warehouse")
public class WarehouseController {

    private final WarehouseService warehouseService;

    @Autowired
    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<Warehouse>> save(@Valid @RequestBody WarehouseAddDto dto) {
        return warehouseService.save(dto);
    }

    @GetMapping("/get{id}")
    public ResponseEntity<ApiResponse<Warehouse>> get(@PathVariable(value = "id") Long id) {
        return warehouseService.get(id);
    }

    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse<Page<Warehouse>>> getList(@RequestParam(required = false,defaultValue = "0") int page) {
        return warehouseService.getList(page);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Warehouse>> update(@Valid @PathVariable(value = "id") Long id, @RequestBody WarehouseDto dto) {
        return warehouseService.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable(value = "id") Long id) {
        return warehouseService.delete(id);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
