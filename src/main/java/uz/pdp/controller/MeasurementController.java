package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.domain.Measurement;
import uz.pdp.model.MeasurementAddDto;
import uz.pdp.model.MeasurementDto;
import uz.pdp.model.response.ApiResponse;
import uz.pdp.service.MeasurementService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/measurement")
public class MeasurementController {
    private final MeasurementService measurementService;

    @Autowired
    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<Measurement>>save(@Valid  @RequestBody MeasurementAddDto measurementDto){
        return measurementService.save(measurementDto);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<Measurement>> get(@PathVariable(value = "id") Long id) {
        return measurementService.get(id);
    }

    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse<Page<Measurement>>> getList(@RequestParam(required = false,defaultValue = "0") int page) {
        return measurementService.getList(page);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Measurement>> update(@Valid @PathVariable(value = "id") Long id, @RequestBody MeasurementDto dto) {
        return measurementService.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable(value = "id") Long id) {
        return measurementService.delete(id);
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
