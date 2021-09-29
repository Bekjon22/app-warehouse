package uz.pdp.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class OutputProductAddDto {
    @NotNull(message = "Amount  cannot be null")
    private Double amount;

    @NotNull(message = "Price  cannot be null")
    private Double price;

    @NotNull(message = "Product id cannot be null")
    private Long productId;

    @NotNull(message = "Output id cannot be null")
    private Long outputId;
}
