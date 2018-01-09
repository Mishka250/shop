package sh.shop.addd.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "order_")
public class Order {
    @Id
    @Column(name = "id_order")
    @GeneratedValue
    private Integer id;

    @Column(name = "total_price")
    private Double totalPrice;

    @OneToOne
    private User user;

    @Column(name = "date_of_order")
    private Date orderDate;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable( joinColumns = { @JoinColumn(name = "id_order") }, inverseJoinColumns = { @JoinColumn(name = "id_detail") })
    List<OrderDetails> details;
}
