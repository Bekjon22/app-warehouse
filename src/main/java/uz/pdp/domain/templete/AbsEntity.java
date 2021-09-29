package uz.pdp.domain.templete;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@MappedSuperclass
public abstract class AbsEntity {

    @Id    // primary  key
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // sequence
    private Long id;


    @Column
    private String name;

    @Column
    private boolean active = true;

}
