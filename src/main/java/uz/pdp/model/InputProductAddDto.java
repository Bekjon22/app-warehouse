package uz.pdp.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Setter
@Getter
public class InputProductAddDto {
    @NotBlank(message = "Amount cannot be empty")
    private Double amount;

    @NotBlank(message = "Price cannot be empty")
    private Double price;

    private LocalDate expireDate;

    @NotBlank(message = "Product id cannot be empty")
    private Long productId;

    @NotBlank(message = "Input id cannot be empty")
    private Long inputId;
}
