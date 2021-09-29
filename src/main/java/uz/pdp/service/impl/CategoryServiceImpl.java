package uz.pdp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.domain.Category;
import uz.pdp.model.CategoryAddDto;
import uz.pdp.model.CategoryDto;
import uz.pdp.model.response.ApiResponse;
import uz.pdp.model.response.ApiResponse1;
import uz.pdp.repository.CategoryRepo;
import uz.pdp.service.CategoryService;

import java.util.Optional;
import static uz.pdp.model.response.ApiResponse.response;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepo categoryRepo;

    @Autowired
    public CategoryServiceImpl(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public ResponseEntity<ApiResponse<Category>> save(CategoryAddDto dto) {
        Category category = new Category();
        category.setName(dto.getName());
        if (dto.getParentCategoryId()!=null){
            Optional<Category> optionalCategory = categoryRepo.findById(dto.getParentCategoryId());
            if (!optionalCategory.isPresent()){
                return response(String.format("This Category id, %s not found!", dto.getParentCategoryId()),HttpStatus.NOT_FOUND);
            }
            category.setParentCategory(optionalCategory.get());
        }

        return response(categoryRepo.save(category));

    }

    @Override
    public ResponseEntity<ApiResponse<Category>> get(Long id) {
        return validate(id);
    }

    @Override
    public ResponseEntity<ApiResponse<Page<Category>>> getList(int page) {
        Pageable pageable = PageRequest.of(page,10);
        Page<Category> categories = categoryRepo.findAll(pageable);
        if (categories.isEmpty()) {
            return response("Categories not found", HttpStatus.NOT_FOUND);
        }
        return response(categories);
    }

    @Override
    public ResponseEntity<ApiResponse<Category>> update(Long id, CategoryDto dto) {
        Optional<Category> optionalCategory = categoryRepo.findById(id);
        if (!optionalCategory.isPresent()) {
            return response(String.format("This Category id, %s not found!", id), HttpStatus.NOT_FOUND);
        }
        Category category = optionalCategory.get();

        if (dto.getParentCategoryId()!=null){
            Optional<Category> optCategory = categoryRepo.findById(dto.getParentCategoryId());
            if (!optCategory.isPresent()){
                return response(String.format("This Category id, %s not found!", dto.getParentCategoryId()), HttpStatus.NOT_FOUND);
            }
            category.setParentCategory(optCategory.get());
        }
        category.setName(dto.getName());
        category.setActive(dto.getActive());
        return response(categoryRepo.save(category));
    }

    @Override
    public ResponseEntity<ApiResponse<Boolean>> delete(Long id) {
        Optional<Category> optionalCategory = categoryRepo.findById(id);
        if (!optionalCategory.isPresent()) {
            return response(String.format("This Category id, %s not found!", id), HttpStatus.NOT_FOUND);
        }
        categoryRepo.delete(optionalCategory.get());
        return response(true);

    }

    @Override
    public ResponseEntity<ApiResponse<Category>> validate(Long id) {

        Optional<Category> categoryOpt = categoryRepo.findById(id);
        if (!categoryOpt.isPresent()) {
            return response(String.format("This Category id, %s not found!", id),HttpStatus.NOT_FOUND);
        }
        Category category = categoryOpt.get();

        if (!category.isActive()) {
            return response(String.format("This Category id, %s inactive!", id), HttpStatus.FORBIDDEN);

        }
        return response(category);
    }

    @Override
    public Category validateCategory(Long id) {
        Optional<Category> categoryOpt = categoryRepo.findById(id);
        if (!categoryOpt.isPresent()) {
            throw new RuntimeException("Category id = " + id + ", not found!");        }
        Category category = categoryOpt.get();

        if (!category.isActive()) {
            throw new RuntimeException("Product id = " + id + ", is inactive!");        }
        return category;

    }


}
