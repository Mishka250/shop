package sh.shop.addd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sh.shop.addd.dto.ReviewDTO;
import sh.shop.addd.entity.Review;
import sh.shop.addd.entity.ReviewDetails;
import sh.shop.addd.entity.User;
import sh.shop.addd.service.AuthorizationService;
import sh.shop.addd.service.ReviewService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @Autowired
    AuthorizationService authorizationService;

    @RequestMapping(value = "/addReview", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ReviewDTO addReview(Integer userId, String text, String type, Integer idProduct){
        User user = authorizationService.getUserBy(userId);
        Review review = reviewService.addReview(user.getUsername(),text,type,idProduct);
        ReviewDTO dto = new ReviewDTO();
        List<ReviewDetails> reviews = review.getReviews();
        List<ReviewDetails> collect = reviews.stream()
                .filter(reviewDetails -> reviewDetails.getUser().equals(user.getUsername())
                        && reviewDetails.getBody().equals(text))
                .sorted(Comparator.comparing(ReviewDetails::getTime))
                .collect(Collectors.toList());
        dto.setTime(collect.get(0).getTime());
        dto.setUserName(user.getUsername());

        dto.setBody(text);
        return dto;
    }

}
