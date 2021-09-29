package uz.pdp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.domain.Attachment;
import uz.pdp.domain.Category;
import uz.pdp.domain.Measurement;
import uz.pdp.domain.Product;
import uz.pdp.model.ProductAddDto;
import uz.pdp.model.ProductDto;
import uz.pdp.model.response.ApiResponse;
import uz.pdp.repository.AttachmentRepo;
import uz.pdp.repository.CategoryRepo;
import uz.pdp.repository.MeasurementRepo;
import uz.pdp.repository.ProductRepo;
import uz.pdp.service.CategoryService;
import uz.pdp.service.ProductService;

import java.util.Optional;

import static uz.pdp.model.response.ApiResponse.response;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final MeasurementRepo measurementRepo;
    private final AttachmentRepo attachmentRepo;
    private final CategoryService categoryService;

    @Autowired
    public ProductServiceImpl(ProductRepo productRepo, CategoryRepo categoryRepo, MeasurementRepo measurementRepo, AttachmentRepo attachmentRepo, CategoryService categoryService) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.measurementRepo = measurementRepo;
        this.attachmentRepo = attachmentRepo;
        this.categoryService = categoryService;
    }

    @Override
    public ResponseEntity<ApiResponse<Product>> save(ProductAddDto dto) {
        boolean exists = productRepo.existsByNameAndCategoryId(dto.getName(), dto.getCategoryId());
        if (exists) {
            return response(String.format("This Product Name [ %s ] already exist inside of this category [ %s ] !", dto.getName(), dto.getCategoryId()), HttpStatus.CONFLICT);

        }

//        ResponseEntity<ApiResponse<Category>> category = categoryService.validate(dto.getCategoryId());
        Category category1 = categoryService.validateCategory(dto.getCategoryId());


        Optional<Measurement> optionalMeasurement = measurementRepo.findById(dto.getMeasurementId());
        if (!optionalMeasurement.isPresent()) {
            return response(String.format("This Measurement id, %s not found!", dto.getMeasurementId()), HttpStatus.NOT_FOUND);
        }
        Optional<Attachment> optionalAttachment = attachmentRepo.findById(dto.getPhotoId());
        if (!optionalAttachment.isPresent()) {
            return response(String.format("This Photo id, %s not found!", dto.getPhotoId()), HttpStatus.NOT_FOUND);
        }
        Long lastId = productRepo.findLastId() + 1;

        Product product = new Product();
        product.setName(dto.getName());
        product.setCode(String.valueOf(lastId));
        product.setCategory(category1);
        product.setMeasurement(optionalMeasurement.get());
        product.setPhoto(optionalAttachment.get());

        return response(productRepo.save(product));
    }

    @Override
    public ResponseEntity<ApiResponse<Product>> get(Long id) {
        return validate(id);
    }

    @Override
    public ResponseEntity<ApiResponse<Page<Product>>> getList(int page) {
        Pageable pageable = PageRequest.of(page, 10);

        Page<Product> products = productRepo.findAll(pageable);
        if (products.isEmpty()) {
            return response("Products not found", HttpStatus.NOT_FOUND);
        }
        return response(products);
    }

    @Override
    public ResponseEntity<ApiResponse<Product>> update(Long id, ProductDto dto) {

        Optional<Product> optionalProduct = productRepo.findById(id);
        if (!optionalProduct.isPresent()) {
            return response(String.format("This Product id, %s not found!", id), HttpStatus.NOT_FOUND);
        }

        boolean exists = productRepo.existsByNameAndCategoryIdAndIdNot(dto.getName(), dto.getCategoryId(), id);
        if (exists) {
            return response(String.format("This Product Name [ %s ] already exist inside of this category [ %s ] !", dto.getName(), dto.getCategoryId()), HttpStatus.CONFLICT);
        }
        Optional<Category> optionalCategory = categoryRepo.findById(dto.getCategoryId());
        if (!optionalCategory.isPresent()) {
            return response(String.format("This Category id, %s not found!", dto.getCategoryId()), HttpStatus.NOT_FOUND);
        }
        Optional<Measurement> optionalMeasurement = measurementRepo.findById(dto.getMeasurementId());
        if (!optionalMeasurement.isPresent()) {
            return response(String.format("This Measurement id, %s not found!", dto.getMeasurementId()), HttpStatus.NOT_FOUND);
        }
        Optional<Attachment> optionalAttachment = attachmentRepo.findById(dto.getPhotoId());
        if (!optionalAttachment.isPresent()) {
            return response(String.format("This Photo id, %s not found!", dto.getPhotoId()), HttpStatus.NOT_FOUND);
        }
        Long lastId = productRepo.findLastId() + 1;
        Product product = optionalProduct.get();
        product.setName(dto.getName());
        product.setActive(dto.getActive());
        product.setCode(String.valueOf(lastId));
        product.setCategory(optionalCategory.get());
        product.setMeasurement(optionalMeasurement.get());
        product.setPhoto(optionalAttachment.get());

        return response(productRepo.save(product));


    }

    @Override
    public ResponseEntity<ApiResponse<Boolean>> delete(Long id) {
        Optional<Product> optionalProduct = productRepo.findById(id);
        if (!optionalProduct.isPresent()) {
            return response(String.format("This Product id, %s not found!", id), HttpStatus.NOT_FOUND);
        }
        productRepo.delete(optionalProduct.get());
        return response(true);

    }

    @Override
    public ResponseEntity<ApiResponse<Product>> validate(Long id) {

        Optional<Product> productOpt = productRepo.findById(id);
        if (!productOpt.isPresent()) {
            return response(String.format("This Product id, %s not found!", id), HttpStatus.NOT_FOUND);
        }
        Product product = productOpt.get();

        if (!product.isActive()) {
            return response(String.format("This Product id, %s inactive!", id), HttpStatus.FORBIDDEN);
        }
        return response(product);
    }

    @Override
    public Product validateProduct(Long id) {

        Optional<Product> productOpt = productRepo.findById(id);
        if (!productOpt.isPresent()) {
            throw new RuntimeException("Product id = " + id + ", not found!");
        }
        Product product = productOpt.get();
        if (!product.isActive()) {
            throw new RuntimeException("Product id = " + id + ", is inactive!");
        }
        return product;
    }


}
