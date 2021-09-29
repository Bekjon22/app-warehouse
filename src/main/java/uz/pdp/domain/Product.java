package uz.pdp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.domain.templete.AbsEntity;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product extends AbsEntity {

    @ManyToOne(optional = false)
    private Category category;

    @Column
    private String code;

    @OneToOne
    private Attachment photo;

    @ManyToOne(optional = false)
    private Measurement measurement;
}
