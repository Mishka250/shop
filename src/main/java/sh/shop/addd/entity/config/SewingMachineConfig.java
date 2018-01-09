package sh.shop.addd.entity.config;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class SewingMachineConfig  implements Configuration{
    private Integer userID;
    private String name;
    private Double price;
    private String type = "machine";
    private String shuffleType;
    private Integer max;
    private Integer min;
    private boolean isDisplaySelected;
    private boolean displayValue;

    public String toJSON(){
        StringBuilder builder = new StringBuilder("{\n");
        builder.append("\"shuffleType\" : \""+shuffleType+"\",\n");
        builder.append("\"max\" : \""+max+"\",\n");
        builder.append("\"min\" : \""+min+"\",\n");
        builder.append("\"isDisplaySelected\" : \""+isDisplaySelected+"\",\n");
        builder.append("\"displayValue\" : \""+displayValue+"\"\n}");

        return builder.toString();

    }

}
