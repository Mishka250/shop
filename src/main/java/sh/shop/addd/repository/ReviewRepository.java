package sh.shop.addd.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sh.shop.addd.entity.Review;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Review,Integer>{

    List<Review> getAllByTypeAndIdProduct(String type, Integer productId);

}
