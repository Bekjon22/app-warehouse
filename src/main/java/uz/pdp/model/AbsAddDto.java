package uz.pdp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
@MappedSuperclass
@Getter
@Setter
public abstract class AbsAddDto implements Serializable {

    @NotBlank(message = "name cannot be empty!")
    private String name;



}
