package sh.shop.addd.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class FurnitureType {
    @Id
    @Column(name = "id_type")
    @GeneratedValue
    private Integer id;

    private String type;

}
