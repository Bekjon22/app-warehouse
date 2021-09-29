package uz.pdp.service;

import uz.pdp.domain.OutputProduct;
import uz.pdp.model.OutputProductAddDto;

import java.util.List;

public interface OutputProductService {
    OutputProduct add(OutputProductAddDto dto);

    List<OutputProduct> addAll(List<OutputProductAddDto> dto);
}
