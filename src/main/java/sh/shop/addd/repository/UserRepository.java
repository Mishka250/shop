package sh.shop.addd.repository;

import org.springframework.data.repository.CrudRepository;
import sh.shop.addd.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    User getByEmailAndPassword(String email, String password);
    User getByUsername(String username);
}
