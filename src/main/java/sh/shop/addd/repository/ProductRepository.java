package sh.shop.addd.repository;

import org.springframework.data.repository.CrudRepository;
import sh.shop.addd.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {
}
