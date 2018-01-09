package sh.shop.addd.entity;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class MarkDetails {
    private Integer idUser;

    private Double mark;
}
