package uz.pdp.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class InputDto implements Serializable {

    @NotNull(message = "Warehouse id cannot be null")
    private Long warehouseId;

    @NotNull(message = "Supplier id cannot be null")
    private Long supplierId;

    @NotNull(message = "Currency id cannot be null")
    private Long currencyId;

    @NotNull(message = "Facture  cannot be null")
    private String factureNumber;
}
