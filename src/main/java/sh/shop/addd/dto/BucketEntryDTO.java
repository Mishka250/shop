package sh.shop.addd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class BucketEntryDTO {
    String type;
    Integer id;
}
