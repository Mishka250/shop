package sh.shop.addd.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class View {

    @Id
    @Column(name = "id_view")
    @GeneratedValue
    private Integer id;

    private String ip;

    private Date date;

    private Integer ref_product;
}
