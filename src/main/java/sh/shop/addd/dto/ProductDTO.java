package sh.shop.addd.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    String creator;
    Double price;
    String name;
    Integer id;
    String type;
    Double mark;
}
