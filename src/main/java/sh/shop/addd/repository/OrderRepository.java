package sh.shop.addd.repository;

import org.springframework.data.repository.CrudRepository;
import sh.shop.addd.entity.Order;

public interface OrderRepository extends CrudRepository<Order, Integer> {
}
