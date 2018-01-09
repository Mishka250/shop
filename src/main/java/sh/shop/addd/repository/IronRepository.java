package sh.shop.addd.repository;

import org.springframework.data.repository.CrudRepository;
import sh.shop.addd.entity.Iron;

import java.util.List;

public interface IronRepository extends CrudRepository<Iron,Integer> {
    List<Iron> getAllByPowerLessThanEqual(Integer power);
    List<Iron> getAllByIsVapor(Boolean value);
}
