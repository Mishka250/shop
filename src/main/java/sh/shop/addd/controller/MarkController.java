package sh.shop.addd.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import sh.shop.addd.dto.BucketDTO;
import sh.shop.addd.dto.BucketEntryDTO;
import sh.shop.addd.entity.Mark;
import sh.shop.addd.entity.MarkDetails;
import sh.shop.addd.entity.Order;
import sh.shop.addd.repository.MarkRepository;
import sh.shop.addd.repository.OrderDetailsRepo;
import sh.shop.addd.repository.OrderRepository;
import sh.shop.addd.repository.UserRepository;

import java.util.stream.Collectors;

@RestController
public class MarkController {

    @Autowired
    MarkRepository markRepository;
    @Autowired
    UserRepository userRepository;
    Jedis jedis = new Jedis("localhost", 6379);
    Gson gson = new Gson();

    @Autowired
    OrderRepository orderRepository;

    @RequestMapping(value = "/mark/{type}/{id}", method = RequestMethod.GET)
    public Double getMark(@PathVariable String type, @PathVariable Integer id) {
        Mark mark = markRepository.findByIdProductAndType(id, type);
        double res = 0;
        if(mark == null || mark.getDetails() == null){
            return res;
        }
        for (MarkDetails details : mark.getDetails()) {
            res += details.getMark();
        }
        System.out.println(res);
        return mark.getDetails().size() == 0 ? 0 : res / mark.getDetails().size();
    }

    @RequestMapping(value = "/mark/add", method = RequestMethod.POST)
    public Mark addMark(String idUser, String mark, String type, String productId) {
        Mark byIdProductAndType = markRepository.findByIdProductAndType(Integer.parseInt(productId), type);
        MarkDetails details = new MarkDetails();
        details.setIdUser(Integer.parseInt(idUser));
        details.setMark(Double.parseDouble(mark));
        byIdProductAndType.getDetails().add(details);
        Mark save = markRepository.save(byIdProductAndType);
        return save;
    }

    @RequestMapping(value = "/hasMark/{userId}/{productId}/{productType}", method = RequestMethod.GET)
    public boolean hasMark(@PathVariable Integer userId, @PathVariable Integer productId, @PathVariable String productType) {
//        Mark mark = markRepository.findByIdProductAndType(productId,productType);
        String s = jedis.get("bucketBought" + userId);
        BucketDTO bucketDTO = gson.fromJson(s, BucketDTO.class);
        return !bucketDTO.getSet().contains(new BucketEntryDTO(productType, productId));
      /*  if(mark!=null){
            return mark.getDetails().stream()
                    .filter(markDetails -> markDetails.getIdUser().equals(userId))
                    .collect(Collectors.toList()).size() != 0;

        }*/
//        return false;
    }
}
