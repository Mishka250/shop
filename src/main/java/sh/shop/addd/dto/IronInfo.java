package sh.shop.addd.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sh.shop.addd.entity.Iron;
import sh.shop.addd.entity.ReviewDetails;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IronInfo extends Info{
    Iron iron;
    List<ReviewDetails> reviews = new ArrayList<>(5);
    Double mark = 0D;
}
