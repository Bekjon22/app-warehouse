package uz.pdp.service;


import uz.pdp.domain.Output;
import uz.pdp.model.OutputAddDto;

public interface OutputService {

    Output add(OutputAddDto dto);
}
