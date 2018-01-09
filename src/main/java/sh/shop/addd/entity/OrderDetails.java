package sh.shop.addd.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderDetails {

    @Id
    @Column(name = "id_order_details")
    @GeneratedValue
    private Integer id;

    @OneToOne(targetEntity = Product.class)
    private Product product;

    @OneToOne(targetEntity = Order.class)
    Order order;


    private Double price;

    private Integer amount;



}
