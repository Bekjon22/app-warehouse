package uz.pdp.service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import uz.pdp.domain.Category;
import uz.pdp.model.CategoryAddDto;
import uz.pdp.model.CategoryDto;
import uz.pdp.model.response.ApiResponse;

public interface CategoryService {
    ResponseEntity<ApiResponse<Category>> save(CategoryAddDto dto);

    ResponseEntity<ApiResponse<Category>> get(Long id);

    ResponseEntity<ApiResponse<Page<Category>>> getList(int page);

    ResponseEntity<ApiResponse<Category>> update(Long id, CategoryDto dto);

    ResponseEntity<ApiResponse<Boolean>> delete(Long id);

    ResponseEntity<ApiResponse<Category>> validate(Long id);

    Category validateCategory (Long id);

}
