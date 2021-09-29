package uz.pdp.service;


import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import uz.pdp.domain.Product;
import uz.pdp.model.ProductAddDto;
import uz.pdp.model.ProductDto;
import uz.pdp.model.response.ApiResponse;

public interface ProductService {


    ResponseEntity<ApiResponse<Product>> save(ProductAddDto dto);

    ResponseEntity<ApiResponse<Product>> get(Long id);

    ResponseEntity<ApiResponse<Page<Product>>> getList(int page);

    ResponseEntity<ApiResponse<Product>> update(Long id, ProductDto dto);

    ResponseEntity<ApiResponse<Boolean>> delete(Long id);

    ResponseEntity<ApiResponse<Product>> validate(Long id);

    Product validateProduct(Long id);
}
