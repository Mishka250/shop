package sh.shop.addd.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import sh.shop.addd.entity.User;
import sh.shop.addd.entity.config.FinalConfiguration;
import sh.shop.addd.entity.config.IronConfig;
import sh.shop.addd.service.AuthorizationService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class AuthorizationController {

    @Autowired
    private AuthorizationService authorizationService;


    Jedis redis = new Jedis("localhost", 6379);
    Gson gson = new Gson();

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public FinalConfiguration login(String email, String password) throws IOException {
        User user = authorizationService.getUserBy(email, password);
        if (user == null) {
            return null;
        }
        String fromRedis = redis.get(user.getId().toString());
        IronConfig configuration = gson.fromJson(fromRedis,IronConfig.class);
        FinalConfiguration configuration1 = new FinalConfiguration();
        configuration1.setUserID(user.getId());
        configuration1.setPath(redis.get("path"+user.getId()));
        configuration1.setConfiguration(configuration);
        return configuration1;
    }

    @RequestMapping(value = "/logout")
    public void logout(HttpServletResponse response) throws IOException {
        response.sendRedirect("/");
//        return idUser;
    }
}
