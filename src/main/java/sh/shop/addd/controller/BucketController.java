package sh.shop.addd.controller;

import com.google.gson.Gson;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import sh.shop.addd.dto.BucketDTO;
import sh.shop.addd.dto.BucketEntryDTO;
import sh.shop.addd.entity.Iron;
import sh.shop.addd.entity.Order;
import sh.shop.addd.entity.OrderDetails;
import sh.shop.addd.entity.User;
import sh.shop.addd.repository.IronRepository;
import sh.shop.addd.repository.OrderDetailsRepo;
import sh.shop.addd.repository.OrderRepository;
import sh.shop.addd.service.AuthorizationService;
import sh.shop.addd.service.SearchService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@RestController
public class BucketController {

    Jedis jedis = new Jedis("localhost", 6379);
    Gson gson = new Gson();
    @Autowired
    OrderRepository repository;
    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    SearchService searchService;

    @Autowired
    OrderDetailsRepo orderDetailsRepo;


    @RequestMapping(value = "/buy/{id}", method = RequestMethod.POST)
    public boolean buy(@PathVariable String id) {

        BucketDTO bucket = gson.fromJson(jedis.get("bucket" + id), BucketDTO.class);
        double price = 0;
        List<OrderDetails> orderDetails = new ArrayList<>();
        if( bucket.getSet().isEmpty())
        {
            return false;
        }
        for (BucketEntryDTO bucketEntryDTO : bucket.getSet()) {
            Iron ironBy = searchService.getIronBy(bucketEntryDTO.getId());
            price += ironBy.getProduct().getPrice();
            OrderDetails e = new OrderDetails();
            e.setAmount(1);
            e.setPrice(ironBy.getProduct().getPrice());
            e.setProduct(ironBy.getProduct());
            orderDetails.add(orderDetailsRepo.save(e));


        }
        User userBy = authorizationService.getUserBy(id);
        Order order = new Order();
        order.setUser(userBy);
        order.setOrderDate(new Date());
        order.setTotalPrice(price);
        order.setDetails(orderDetails);
        String s1 = jedis.get("bucketBought" + id);
        BucketDTO alreadyBought = gson.fromJson(s1, BucketDTO.class);
        alreadyBought.getSet().addAll(bucket.getSet());
        jedis.set("bucketBought" +id,alreadyBought.toString());
        jedis.del("bucket"+id);
        return repository.save(order) != null;
    }
    @RequestMapping(value = "/bucket/{id}", method = RequestMethod.GET)
    public BucketDTO getBucketsItems(@PathVariable Integer id) {

        BucketDTO bucket = gson.fromJson(jedis.get("bucket" + id), BucketDTO.class);
        if(bucket != null && bucket.getSet()!= null)
        {
            jedis.set("path" + id.toString(), "/bucket/"+id);

        }
        return bucket;
    }
    @RequestMapping(value = "/bucket/add", method = RequestMethod.POST)
    public boolean addToBucket(String userID, String type, String productID) {
        BucketDTO bucket = gson.fromJson(jedis.get("bucket" + userID), BucketDTO.class);
        if(bucket == null){
            bucket = new BucketDTO(new HashSet<>(1));
        }
        bucket.getSet().add(new BucketEntryDTO(type, Integer.parseInt(productID)));

        String result = jedis.set("bucket" + userID, gson.toJson(bucket));
        return "OK".equals(result);
    }
    @RequestMapping(value= "/bucket/hasEntry/{userId}/{type}/{productId}", method = RequestMethod.GET)
    public boolean hasEntry(@PathVariable String userId,@PathVariable String type,@PathVariable String productId){
        BucketDTO bucket = gson.fromJson(jedis.get("bucket" + userId), BucketDTO.class);
        if(bucket==null){
            return false;
        }
        return bucket.getSet().contains(new BucketEntryDTO(type,Integer.parseInt(productId)));

    }

    @RequestMapping(value = "/bucket/remove", method = RequestMethod.POST)
    public boolean removeFromBucket(String userID, String type, String productID) {
        BucketDTO bucket = gson.fromJson(jedis.get("bucket" + userID), BucketDTO.class);
        bucket.getSet().remove(new BucketEntryDTO(type, Integer.parseInt(productID)));
        String result = jedis.set("bucket" + userID, gson.toJson(bucket));
        return "OK".equals(result);
    }
}
