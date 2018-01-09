package sh.shop;

import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import sh.shop.addd.entity.config.FilterConfiguration;
import sh.shop.addd.entity.config.FurnitureConfig;
import sh.shop.addd.entity.config.IronConfig;
import sh.shop.addd.entity.config.SewingMachineConfig;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        FurnitureConfig config = new FurnitureConfig();
        config.setLeftValue(2);
        config.setRightValue(5);
        config.setChecked( Arrays.asList("1","2","4","5"));
//        System.out.println(config.toJSON());

        IronConfig config1 = new IronConfig();
        config1.setPower(1000);
        config1.setVapourSelected(true);
        config1.setVapourValue(false);
//        System.out.println(config1.toJSON());

        SewingMachineConfig config2 = new SewingMachineConfig();
        config2.setMax(100);
        config2.setMin(100);
        config2.setDisplaySelected(true);
        config2.setDisplayValue(true);
//        System.out.println(config2.toJSON());
        FilterConfiguration configuration = new FilterConfiguration();
        configuration.setName("name");
        configuration.setPrice(Double.valueOf(100));
        configuration.setFurnitureConfig(null);
        configuration.setIronConfig(config1);
        configuration.setSewingMachineConfig(null);
        Gson gson = new Gson();
        String x = gson.toJson(configuration);
        FilterConfiguration configuration1 = gson.fromJson(x,FilterConfiguration.class);
//        System.out.println(configuration.equals(configuration1));
        System.out.println(x);
        Jedis redis = new Jedis("localhost",6379);
        redis.set("1",gson.toJson(configuration));

//        System.out.println(gson.toJson(configuration1));
//        System.out.println("anfsfja".contains(""));
//        System.out.println(configuration);
    }
}
