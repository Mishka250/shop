package sh.shop.addd.entity.config;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String name;
    private Double price;

    @JsonIgnore
    private String type = "iron";
    private Integer userID;

}
