package uz.pdp.service;


import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import uz.pdp.domain.User;
import uz.pdp.model.UserAddDto;
import uz.pdp.model.response.ApiResponse;

public interface UserService {

    ResponseEntity<ApiResponse<User>> save(UserAddDto dto);

    ResponseEntity<ApiResponse<User>> get(Long id);

    ResponseEntity<ApiResponse<Page<User>>> getList(int page);

    ResponseEntity<ApiResponse<Boolean>> delete(Long id);
}
