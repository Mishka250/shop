package sh.shop.addd.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Furniture {
    @Id
    @Column(name = "id_furniture")
    @GeneratedValue
    private Integer id;

    private Integer amount;

    @OneToOne
    private FurnitureType type;

    @OneToOne
    private Product product;
}
