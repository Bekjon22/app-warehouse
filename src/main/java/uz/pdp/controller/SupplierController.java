package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.domain.Supplier;
import uz.pdp.model.SupplierAddDto;
import uz.pdp.model.SupplierDto;
import uz.pdp.model.response.ApiResponse;
import uz.pdp.service.SupplierService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController {
    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<Supplier>> save(@Valid  @RequestBody SupplierAddDto dto){
        return supplierService.save(dto);
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<Supplier>> get(@PathVariable(value = "id") Long id) {
        return supplierService.get(id);
    }

    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse<Page<Supplier>>> getList(@RequestParam(required = false,defaultValue = "0") int page) {
        return supplierService.getList(page);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Supplier>> update(@Valid @PathVariable(value = "id") Long id, @RequestBody SupplierDto dto) {
        return supplierService.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable(value = "id") Long id) {
        return supplierService.delete(id);
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
