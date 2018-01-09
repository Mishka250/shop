package sh.shop.addd.service;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sh.shop.addd.entity.Review;
import sh.shop.addd.entity.ReviewDetails;
import sh.shop.addd.repository.ReviewRepository;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    public List<Review> getAllReviews(String type, Integer id){
        return reviewRepository.getAllByTypeAndIdProduct(type, id);
    }
    public Review addReview(String username, String body, String type, Integer idProduct) {
        Review review = reviewRepository.findByTypeAndIdProduct(type, idProduct);
        if(review == null) {
            review = new Review();
        }
        review.setIdProduct(idProduct);
        ReviewDetails details = new ReviewDetails();
        details.setBody(body);
        details.setTime(DateTime.now());
        details.setUser(username);
        review.setType(type);
        review.getReviews().add(details);
        Review save = reviewRepository.save(review);
        return save;
    }
}
