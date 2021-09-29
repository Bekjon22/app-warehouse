package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.domain.InputProduct;
import uz.pdp.model.InputProductAddDto;
import uz.pdp.model.response.ApiResponse;
import uz.pdp.model.response.ProductReport;
import uz.pdp.service.InputProductService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/input/product")
public class InputProductController {

    private final InputProductService inputProductService;

    @Autowired
    public InputProductController(InputProductService inputProductService) {
        this.inputProductService = inputProductService;
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<InputProduct>> save(@Valid @RequestBody InputProductAddDto dto){
        return inputProductService.save(dto);
    }

    @PostMapping("/add/all")
    public ResponseEntity<ApiResponse<List<InputProduct>>> addAll(@Valid @RequestBody List<InputProductAddDto> dto) {
        return inputProductService.addAll(dto);
    }

    // todo Dashboard

    @GetMapping("/get/today")
    public List<ProductReport> get() {
        return inputProductService.get();
    }

    @GetMapping("/get/date")
    public List<ProductReport> get(@RequestParam String date) {
        return inputProductService.get(date);
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
