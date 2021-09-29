package uz.pdp.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
public class ClientDto extends AbsDto implements Serializable {

    @Size(min = 7,max = 12,message = "Phone number should be between 7 - 12!")
    private String phoneNumber;
}
