package uz.pdp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.domain.Category;
import uz.pdp.model.CategoryAddDto;
import uz.pdp.model.CategoryDto;
import uz.pdp.model.response.ApiResponse;
import uz.pdp.service.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

//    @PreAuthorize(value = "hasRole('ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<Category>> save(@RequestBody CategoryAddDto dto){
        return categoryService.save(dto);
    }

//    @PreAuthorize(value = "hasAnyRole('ADMIN','USER','GUEST')")
    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<Category>> get(@PathVariable(value = "id") Long id) {
        return categoryService.get(id);
    }
//    @PreAuthorize(value = "hasAnyRole('ADMIN','USER','GUEST')")
    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse<Page<Category>>> getList(@RequestParam(required = false,defaultValue = "0") int page) {
        return categoryService.getList(page);
    }

//    @PreAuthorize(value = "hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Category>> update(@PathVariable(value = "id") Long id, @RequestBody CategoryDto dto) {
        return categoryService.update(id, dto);
    }

//    @PreAuthorize(value = "hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable(value = "id") Long id) {
        return categoryService.delete(id);
    }



}
