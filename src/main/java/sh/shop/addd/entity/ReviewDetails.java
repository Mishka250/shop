package sh.shop.addd.entity;

import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class ReviewDetails {
    private String user;

    private String body;

    private DateTime time;
}
