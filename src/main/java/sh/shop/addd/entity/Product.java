package sh.shop.addd.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Product {
    @Id
    @Column(name = "id_product")
    @GeneratedValue
    private Integer id;

    private Double price;

    private Integer amount;

    private String name;

    @OneToOne
    private Type type;

    private String creator;

}
