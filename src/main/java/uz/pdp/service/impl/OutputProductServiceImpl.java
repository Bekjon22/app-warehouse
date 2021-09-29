package uz.pdp.service.impl;

import org.springframework.stereotype.Service;
import uz.pdp.domain.Output;
import uz.pdp.domain.OutputProduct;
import uz.pdp.domain.Product;
import uz.pdp.helper.Utils;
import uz.pdp.model.OutputProductAddDto;
import uz.pdp.repository.OutputProductRepo;
import uz.pdp.repository.OutputRepo;
import uz.pdp.service.OutputProductService;
import uz.pdp.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OutputProductServiceImpl implements OutputProductService {

    private final OutputProductRepo outputProductRepo;
    private final OutputRepo outputRepo;
    private final ProductService productService;

    public OutputProductServiceImpl(OutputProductRepo outputProductRepo, OutputRepo outputRepo, ProductService productService) {
        this.outputProductRepo = outputProductRepo;
        this.outputRepo = outputRepo;
        this.productService = productService;
    }

    @Override
    public OutputProduct add(OutputProductAddDto dto) {
        return addOne(dto);
    }

    @Override
    public List<OutputProduct> addAll(List<OutputProductAddDto> dto) {
        List<OutputProduct> outputProducts = new ArrayList<>();
        dto.forEach(outputProductAddDto -> outputProducts.add(addOne(outputProductAddDto)));
        return outputProductRepo.saveAll(outputProducts);
    }

    public OutputProduct addOne(OutputProductAddDto dto) {

        Optional<Output> optionalOutput = outputRepo.findById(dto.getOutputId());
        if (!optionalOutput.isPresent()) {
            throw new RuntimeException("Output id = " + dto.getOutputId() + " not found!");
        }

        Product product = productService.validateProduct(dto.getProductId());
        Output output = optionalOutput.get();

        OutputProduct outputProduct = new OutputProduct();
        outputProduct.setProduct(product);
        outputProduct.setOutput(output);
        outputProduct.setAmount(dto.getAmount());
        outputProduct.setPrice(dto.getPrice());
        return outputProductRepo.save(outputProduct);
    }
}
