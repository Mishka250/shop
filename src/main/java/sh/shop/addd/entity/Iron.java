package sh.shop.addd.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Iron {
    @Id
    @Column(name = "id_iron")
    @GeneratedValue
    private Integer id;

    @OneToOne
    private IronType ironType;

    private Integer power;

    @Column(name = "is_vapor")
    private Boolean isVapor;

    @OneToOne(fetch = FetchType.EAGER)
    private Product product;
}
