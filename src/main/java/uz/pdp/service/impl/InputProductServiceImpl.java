package uz.pdp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.domain.Input;
import uz.pdp.domain.InputProduct;
import uz.pdp.domain.Product;
import uz.pdp.model.InputProductAddDto;
import uz.pdp.model.response.ApiResponse;
import uz.pdp.model.response.ProductReport;
import uz.pdp.repository.InputProductRepo;
import uz.pdp.repository.InputRepo;
import uz.pdp.service.InputProductService;
import uz.pdp.service.ProductService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static uz.pdp.model.response.ApiResponse.response;

@Service
public class InputProductServiceImpl implements InputProductService {
    private final InputProductRepo inputProductRepo;
    private final InputRepo inputRepo;
    private final ProductService productService;

    @Autowired
    public InputProductServiceImpl(InputProductRepo inputProductRepo, InputRepo inputRepo, ProductService productService) {
        this.inputProductRepo = inputProductRepo;
        this.inputRepo = inputRepo;
        this.productService = productService;
    }

    @Override
    public ResponseEntity<ApiResponse<InputProduct>> save(InputProductAddDto dto) {
        return response(addOne(dto));
    }

    @Override
    public ResponseEntity<ApiResponse<List<InputProduct>>> addAll(List<InputProductAddDto> dto) {
        List<InputProduct> inputProducts = new ArrayList<>();
        dto.forEach(inputProductAddDto -> inputProducts.add(addOne(inputProductAddDto)));
        return response(inputProductRepo.saveAll(inputProducts));
    }

    @Override
    public List<ProductReport> get() {
        LocalDate today = LocalDate.now();
        return inputProductRepo.findAllByDate(today);
    }

    @Override
    public List<ProductReport> get(String date) {
        try {
            LocalDate parse = LocalDate.parse(date);
            return inputProductRepo.findAllByDate(parse);
        } catch (Exception e) {
            throw new RuntimeException("Wrong date format is included (User yyyy-mm-dd");
        }
    }

    public InputProduct addOne(InputProductAddDto dto) {

        Optional<Input> optionalInput = inputRepo.findById(dto.getInputId());
        if (!optionalInput.isPresent()) {
            throw new RuntimeException("Input id = " + dto.getInputId() + " not found!");
        }

        Product product = productService.validateProduct(dto.getProductId());
        Input input = optionalInput.get();

        InputProduct inputProduct = new InputProduct();
        inputProduct.setProduct(product);
        inputProduct.setInput(input);
        inputProduct.setAmount(dto.getAmount());
        inputProduct.setPrice(dto.getPrice());
        inputProduct.setExpireDate(dto.getExpireDate());

        return inputProductRepo.save(inputProduct);
    }

}
