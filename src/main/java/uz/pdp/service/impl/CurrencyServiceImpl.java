package uz.pdp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.common.MapstructMapper;
import uz.pdp.domain.Currency;
import uz.pdp.model.CurrencyAddDto;
import uz.pdp.model.CurrencyDto;
import uz.pdp.model.response.ApiResponse;
import uz.pdp.repository.CurrencyRepo;
import uz.pdp.service.CurrencyService;

import java.util.Optional;

import static uz.pdp.model.response.ApiResponse.response;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepo currencyRepo;
    private final MapstructMapper mapstructMapper;

    @Autowired
    public CurrencyServiceImpl(CurrencyRepo currencyRepo, MapstructMapper mapstructMapper) {
        this.currencyRepo = currencyRepo;
        this.mapstructMapper = mapstructMapper;
    }

    @Override
    public ResponseEntity<ApiResponse<Currency>> save(CurrencyAddDto dto) {
        boolean existsByName = currencyRepo.existsByName(dto.getName());
        if (existsByName){
            return response( String.format("This Currency Name [ %s ] already exist!", dto.getName()), HttpStatus.CONFLICT);
        }
        return response(currencyRepo.save(mapstructMapper.toCurrency(dto)));
    }

    @Override
    public ResponseEntity<ApiResponse<Currency>> get(Long id) {
        Optional<Currency> optionalCurrency = currencyRepo.findById(id);
        if (!optionalCurrency.isPresent()) {
            return response(String.format("This Currency id, %s not found!", id), HttpStatus.NOT_FOUND);
        }
        return response(optionalCurrency.get());
    }

    @Override
    public ResponseEntity<ApiResponse<Page<Currency>>> getList(int page) {
        Pageable pageable = PageRequest.of(page,10);
        Page<Currency> currencies = currencyRepo.findAll(pageable);
        if (currencies.isEmpty()) {
            return response("Currencies not found", HttpStatus.NOT_FOUND);
        }
        return response(currencies);
    }

    @Override
    public ResponseEntity<ApiResponse<Currency>> update(Long id, CurrencyDto dto) {
        Optional<Currency> optionalCurrency = currencyRepo.findById(id);
        if (!optionalCurrency.isPresent()) {
            return response(String.format("This Currency id, %s not found!", id), HttpStatus.NOT_FOUND);
        }
        Currency byNameAndIdNot = currencyRepo.findByNameAndIdNot(dto.getName(), id);
        if (byNameAndIdNot != null) {
            return response(String.format("This Currency name , %s already exist!", dto.getName()), HttpStatus.CONFLICT);
        }
        return response(currencyRepo.save(mapstructMapper.toCurrency(optionalCurrency.get(),dto)));
    }

    @Override
    public ResponseEntity<ApiResponse<Boolean>> delete(Long id) {
        Optional<Currency> optionalCurrency = currencyRepo.findById(id);
        if (!optionalCurrency.isPresent()) {
            return response(String.format("This Currency id, %s not found!", id), HttpStatus.NOT_FOUND);
        }
        currencyRepo.delete(optionalCurrency.get());
        return response(true);
    }

    @Override
    public Currency validate(Long id) {
        Optional<Currency> optionalCurrency = currencyRepo.findById(id);
        if (!optionalCurrency.isPresent()) {
            throw new RuntimeException("Currency id = " + id + ", not found!");
        }
        Currency currency = optionalCurrency.get();
        if (!currency.isActive()) {
            throw new RuntimeException("Currency id = " + id + ", is inactive!");
        }
        return currency;
    }


}
