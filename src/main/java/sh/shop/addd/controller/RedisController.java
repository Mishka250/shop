package sh.shop.addd.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import sh.shop.addd.entity.config.*;

@RestController
public class RedisController {
    private  Jedis redis = new Jedis("localhost",6379);
    private  Gson jsonParser = new Gson();
    @RequestMapping(value = "/getFilters/iron/{userID}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public FinalConfiguration getIronFilters(@PathVariable  Integer userID) {
        String fromRedis = redis.get(userID.toString());
        IronConfig configuration = jsonParser.fromJson(fromRedis,IronConfig.class);
        FinalConfiguration configuration1 = new FinalConfiguration();
        configuration1.setUserID(userID);
        configuration1.setPath(redis.get("path"+userID));
        configuration1.setConfiguration(configuration);
        return configuration1;
    } @RequestMapping(value = "/getFilters/sewingMachine/{userID}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public FinalConfiguration getMachineFilters(@PathVariable  Integer userID) {
        String fromRedis = redis.get(userID.toString());
//        IronConfig configuration = jsonParser.fromJson(fromRedis,IronConfig.class);
        FinalConfiguration configuration1 = new FinalConfiguration();
        configuration1.setUserID(userID);
        configuration1.setPath(redis.get("path"+userID));
        configuration1.setConfiguration(null);
        return configuration1;
    } @RequestMapping(value = "/getFilters/furniture/{userID}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public FinalConfiguration getFurnitureFilters(@PathVariable  Integer userID) {
        String fromRedis = redis.get(userID.toString());
//        IronConfig configuration = jsonParser.fromJson(fromRedis,IronConfig.class);
        FinalConfiguration configuration1 = new FinalConfiguration();
        configuration1.setUserID(userID);
        configuration1.setPath(redis.get("path"+userID));
        configuration1.setConfiguration(null);
        return configuration1;
    }
    @RequestMapping(value = "/setFilters/iron", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IronConfig setIronFilters( @RequestBody String json) {
        IronConfig  configuration = jsonParser.fromJson(json,IronConfig.class);
        String s = redis.set(configuration.getUserID().toString(), jsonParser.toJson(configuration));
        redis.set("path"+configuration.getUserID(), "/iron");
        System.out.println();
        return configuration;
    }
    @RequestMapping(value = "/setFilters/sewingMachine", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public SewingMachineConfig setMachineFilters( @RequestBody String json) {
        SewingMachineConfig configuration = jsonParser.fromJson(json,SewingMachineConfig.class);
//        String s = redis.set("ironconfig" + configuration.getUserID().toString(), jsonParser.toJson(configuration));
        redis.set("path"+configuration.getUserID(), "/sewingMachine");
        System.out.println();
        return configuration;
    }
    @RequestMapping(value = "/setFilters/furniture", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public FurnitureConfig setFurnitureFilters( @RequestBody String json) {
        FurnitureConfig  configuration = jsonParser.fromJson(json,FurnitureConfig.class);
//        String s = redis.set("ironconfig" + configuration.getUserID().toString(), jsonParser.toJson(configuration));
        redis.set("path"+configuration.getUserID(), "/furniture");
        System.out.println();
        return configuration;
    }
}
