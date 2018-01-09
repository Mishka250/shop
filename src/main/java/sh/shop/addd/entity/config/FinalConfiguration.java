package sh.shop.addd.entity.config;


import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class FinalConfiguration {

    Integer userID;

    String path;

    Configuration configuration;

    public FinalConfiguration(Integer userId) {
        userID = userId;
    }
}
