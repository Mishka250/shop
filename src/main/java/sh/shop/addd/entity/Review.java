package sh.shop.addd.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Document
public class Review {
    @Id
    @GeneratedValue
    private ObjectId id;

    private Integer idProduct;

    private String type;

    List<ReviewDetails> reviews = new ArrayList<>(5);
}
