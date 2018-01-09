package sh.shop.addd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class BucketDTO {
    Set<BucketEntryDTO> set;
}
