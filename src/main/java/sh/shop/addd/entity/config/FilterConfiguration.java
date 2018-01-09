package sh.shop.addd.entity.config;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class FilterConfiguration  {
    private String name;
    private Double price;
//    private String creator;
    private FurnitureConfig furnitureConfig;
    private IronConfig ironConfig;
    private SewingMachineConfig sewingMachineConfig;

}
