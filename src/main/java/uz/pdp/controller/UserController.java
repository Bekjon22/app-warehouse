package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.domain.User;
import uz.pdp.model.UserAddDto;
import uz.pdp.model.response.ApiResponse;
import uz.pdp.service.UserService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<User>>save(@Valid @RequestBody UserAddDto dto){
        return userService.save(dto);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<User>> get(@PathVariable(value = "id") Long id) {
        return userService.get(id);
    }

    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse<Page<User>>> getList(@RequestParam(required = false,defaultValue = "0") int page) {
        return userService.getList(page);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable(value = "id") Long id) {
        return userService.delete(id);
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
