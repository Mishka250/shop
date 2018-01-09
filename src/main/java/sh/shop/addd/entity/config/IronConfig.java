package sh.shop.addd.entity.config;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class IronConfig implements Configuration {
    private Integer power;
    private Integer userID;
    private boolean isVapourSelected;
    private boolean isVapourValue;
    private String name;
    private Double price;
    private String type = "iron";

    public IronConfig(Integer power, boolean isVapourSelected, boolean isVapourValue, String name, Double price) {
        this.power = power;
        this.isVapourSelected = isVapourSelected;
        this.isVapourValue = isVapourValue;
        this.name = name;
        this.price = price;
    }
}
