package sh.shop.addd.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sh.shop.addd.entity.Mark;

@Repository
public interface MarkRepository  extends MongoRepository<Mark,Integer>{
    Mark findByIdProductAndType(Integer idProduct, String type);
}
