package uz.pdp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto extends AbsDto {

    private Long parentCategoryId;
}
