package sh.shop.addd.entity.config;


import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class FurnitureConfig implements Configuration {
    private Integer leftValue;
    private Integer rightValue;
    private List<String> checked;

}
