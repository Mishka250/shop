package sh.shop.addd.controller;

import com.google.gson.Gson;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import sh.shop.addd.entity.config.*;

@RestController
public class RedisController {
    private  Jedis redis = new Jedis("localhost",6379);
    private  Gson jsonParser = new Gson();
    @RequestMapping(value = "/getFilters/iron/{userID}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public FinalConfiguration getFilters(@PathVariable  Integer userID) {
        String fromRedis = redis.get(userID.toString());
        IronConfig configuration = jsonParser.fromJson(fromRedis,IronConfig.class);
        FinalConfiguration configuration1 = new FinalConfiguration();
        configuration1.setUserID(userID);
        configuration1.setPath(redis.get("path"+userID));
        configuration1.setConfiguration(configuration);
        return configuration1;
    }

   /* @RequestMapping(value = "/setFilters/iron/" +
            "{userID}/{price}/" +
            "{name}/{power}/{hasVapour}/{vapourValue}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean setFilters(@PathVariable Integer userID,
                              @PathVariable Double price,
                              @PathVariable String name,
                              @PathVariable Integer power,
                              @PathVariable String hasVapour,
                              @PathVariable String vapourValue){
        IronConfig configuration = new IronConfig(power,Boolean.valueOf(hasVapour),Boolean.valueOf(vapourValue),name,price);
        String s = redis.set(userID.toString(), jsonParser.toJson(configuration));
        redis.set("path"+userID, "/iron");
        return "OK".equals(s);
    }*/

    @RequestMapping(value = "/setFilters/iron", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean setFilters( @RequestBody String json) {
        IronConfig  configuration = jsonParser.fromJson(json,IronConfig.class);
        String s = redis.set(configuration.getUserID().toString(), jsonParser.toJson(configuration));
        redis.set("path"+configuration.getUserID(), "/iron");
        System.out.println();
        return false;
    }
}
