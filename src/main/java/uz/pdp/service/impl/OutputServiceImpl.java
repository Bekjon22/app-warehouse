package uz.pdp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.domain.Client;
import uz.pdp.domain.Currency;
import uz.pdp.domain.Output;
import uz.pdp.domain.Warehouse;
import uz.pdp.helper.Utils;
import uz.pdp.model.OutputAddDto;
import uz.pdp.repository.OutputRepo;
import uz.pdp.service.ClientService;
import uz.pdp.service.CurrencyService;
import uz.pdp.service.OutputService;
import uz.pdp.service.WarehouseService;

import java.time.LocalDateTime;

@Service
public class OutputServiceImpl implements OutputService {
    private final OutputRepo outputRepo;
    private final ClientService clientService;
    private final CurrencyService currencyService;
    private final WarehouseService warehouseService;


    @Autowired
    public OutputServiceImpl(OutputRepo outputRepo, ClientService clientService, CurrencyService currencyService, WarehouseService warehouseService) {
        this.outputRepo = outputRepo;
        this.clientService = clientService;
        this.currencyService = currencyService;
        this.warehouseService = warehouseService;
    }

    @Override
    public Output add(OutputAddDto dto) {

        Client client = clientService.validate(dto.getClientId());
        Currency currency = currencyService.validate(dto.getCurrencyId());
        Warehouse warehouse = warehouseService.validate(dto.getWarehouseId());

        Output output = new Output();
        output.setCode(Utils.generateCode());
        output.setCurrency(currency);
        output.setDate(LocalDateTime.now());
        output.setFactureNumber(dto.getFactureNumber());
        output.setClient(client);
        output.setWarehouse(warehouse);

        return outputRepo.save(output);
    }
}
