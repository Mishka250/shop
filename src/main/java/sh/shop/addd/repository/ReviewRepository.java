package sh.shop.addd.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import sh.shop.addd.entity.Review;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Review,ObjectId>{

    Review findByTypeAndIdProduct(String type, Integer idProduct);
    List<Review> getAllByTypeAndIdProduct(String type, Integer productId);

}
