package sh.shop.addd.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class SewingMachine {
    @Id
    @Column(name = "id_sewing_machine")
    @GeneratedValue
    private Integer id;

    @OneToOne
    private SewingMachineType type;

    @Column(name = "type_of_shuttle")
    private String shuttleType;

    @Column(name = "max_amount_of_operations")
    private Integer maxOperationsNumber;

    @Column(name = "min_amount_of_operations")
    private Integer minOperationsNumber;

    @Column(name = "is_display")
    private Boolean hasDisplay;

    @OneToOne
    private Product product;

}
