package sh.shop.addd.repository;

import org.springframework.data.repository.CrudRepository;
import sh.shop.addd.entity.OrderDetails;

public interface OrderDetailsRepo  extends CrudRepository<OrderDetails,Integer>{
}
