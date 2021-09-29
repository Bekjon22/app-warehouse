package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.domain.Currency;
import uz.pdp.model.CurrencyAddDto;
import uz.pdp.model.CurrencyDto;
import uz.pdp.model.response.ApiResponse;
import uz.pdp.service.CurrencyService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/currency")
public class CurrencyController {
    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }




    @PostMapping("/save")
    public ResponseEntity<ApiResponse<Currency>>save(@Valid @RequestBody CurrencyAddDto dto){
        return currencyService.save(dto);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<Currency>> get(@PathVariable(value = "id") Long id) {
        return currencyService.get(id);
    }

    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse<Page<Currency>>> getList(@RequestParam(required = false,defaultValue = "0") int page) {
        return currencyService.getList(page);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Currency>> update(@Valid @PathVariable(value = "id") Long id, @RequestBody CurrencyDto dto) {
        return currencyService.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable(value = "id") Long id) {
        return currencyService.delete(id);
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
