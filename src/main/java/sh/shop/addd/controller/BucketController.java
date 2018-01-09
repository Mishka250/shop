package sh.shop.addd.controller;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import sh.shop.addd.dto.BucketDTO;
import sh.shop.addd.dto.BucketEntryDTO;

import java.util.HashSet;

@RestController
public class BucketController {

    Jedis jedis = new Jedis("localhost", 6379);
    Gson gson = new Gson();

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
