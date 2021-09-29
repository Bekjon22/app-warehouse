package uz.pdp.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class OutputAddDto {

    @NotNull(message = "Facture  cannot be null")
    private String factureNumber;

    @NotNull(message = "Warehouse id cannot be null")
    private Long warehouseId;

    @NotNull(message = "Currency id cannot be null")
    private Long currencyId;

    @NotNull(message = "Client id  cannot be null")
    private Long clientId;
}
