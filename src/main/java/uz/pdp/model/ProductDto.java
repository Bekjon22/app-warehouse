package uz.pdp.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProductDto extends AbsDto {

    @NotNull(message = "Category id cannot be null!")
    private Long categoryId;

    @NotNull(message = "Photo id cannot be null!")
    private Long photoId;

    @NotNull(message = "Measurement id cannot be null!")
    private Long measurementId;

}
