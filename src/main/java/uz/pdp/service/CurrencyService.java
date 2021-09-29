package uz.pdp.service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import uz.pdp.domain.Currency;
import uz.pdp.model.CurrencyAddDto;
import uz.pdp.model.CurrencyDto;
import uz.pdp.model.response.ApiResponse;

public interface CurrencyService {
    ResponseEntity<ApiResponse<Currency>> save(CurrencyAddDto dto);

    ResponseEntity<ApiResponse<Currency>> get(Long id);

    ResponseEntity<ApiResponse<Page<Currency>>> getList(int page);

    ResponseEntity<ApiResponse<Currency>> update(Long id, CurrencyDto dto);

    ResponseEntity<ApiResponse<Boolean>> delete(Long id);

    Currency validate(Long id);
}
