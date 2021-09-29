package uz.pdp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryAddDto extends AbsAddDto {

    private Long parentCategoryId;
}
