package sh.shop.addd.repository;

import org.springframework.data.repository.CrudRepository;
import sh.shop.addd.entity.Furniture;

import java.util.List;

public interface FurnitureRepository extends CrudRepository<Furniture, Integer> {
    List<Furniture> getAllByAmountBetween(Integer left, Integer right);
    List<Furniture> getAllByType(String type);
    List<Furniture> getAllByAmountBetweenAndType(Integer left, Integer right, String type);

}
