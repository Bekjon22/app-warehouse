package uz.pdp.model;

import lombok.Getter;
import lombok.Setter;
import uz.pdp.domain.Warehouse;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserAddDto implements Serializable {

    @NotNull(message = "Firstname cannot be null!")
    private String firstName;

    private String lastName;

    @NotNull(message = "Phone number cannot be null!")
    @Size(min = 7,max = 12,message = "phone number should be between 7-12")
    private String phoneNumber;

    @NotNull(message = "Password  cannot be null!")
    private String password;

    @NotNull(message = "Warehouse id  cannot be null!")
    private Set<Long> warehousesIds;

}
