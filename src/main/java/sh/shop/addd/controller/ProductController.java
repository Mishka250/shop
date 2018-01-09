package sh.shop.addd.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import sh.shop.addd.dto.IronInfo;
import sh.shop.addd.dto.ProductDTO;
import sh.shop.addd.entity.*;
import sh.shop.addd.entity.config.IronConfig;
import sh.shop.addd.repository.ViewRepository;
import sh.shop.addd.service.ReviewService;
import sh.shop.addd.service.SearchService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class ProductController {
    @Autowired
    private SearchService searchService;

    @Autowired
    private ViewRepository repository;
    private static Gson jsonParser = new Gson();

    @Autowired
    private ReviewService reviewService;

    Jedis redis = new Jedis("localhost", 6379);



    @RequestMapping(value = "/sewingMachine", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductDTO> getMachines() {
        Iterable<SewingMachine> machinesIter= searchService.getMachines();
        List<SewingMachine> machines = new ArrayList<>();
        machinesIter.forEach(machines::add);

        return machines.stream().map(sewingMachine -> { ProductDTO machineDTO = new ProductDTO();
            machineDTO.setId(sewingMachine.getId());
            machineDTO.setPrice(sewingMachine.getProduct().getPrice());
            machineDTO.setName(sewingMachine.getProduct().getName());
            machineDTO.setType("machine");
            return machineDTO;}).collect(Collectors.toList());

    }
    @RequestMapping(value = "/furniture", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductDTO> getFurniture() {
        Iterable<Furniture> machinesIter= searchService.getFurnitures();
        List<Furniture> furnitures = new ArrayList<>();
        machinesIter.forEach(furnitures::add);

        return furnitures.stream().map(furniture -> {   ProductDTO furnitureDto = new ProductDTO();
            furnitureDto.setId(furniture.getId());
            furnitureDto.setPrice(furniture.getProduct().getPrice());
            furnitureDto.setType(furniture.getType().getType());
            furnitureDto.setName("furniture");
            return furnitureDto;}).collect(Collectors.toList());

    }
    @RequestMapping(value = "/iron", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductDTO> getIrons( @RequestBody String json) {
        IronConfig  config = jsonParser.fromJson(json,IronConfig.class);
        Iterable<Iron> ironsIterable = searchService.getIrons();
        List<ProductDTO> productsToReturn = new ArrayList<>();
        List<Iron> ironsList = new ArrayList<>();
        ironsIterable.forEach(ironsList::add);
        List<Iron> collected = ironsList.stream()
                .filter(iron ->
                        config.getName()!= null && !config.getName().isEmpty() ?
                                iron.getProduct().getName()
                                        .contains(config.getName()) :
                                true
                )
                .filter(iron ->  config.getPrice() != null && config.getPrice()>0?
                        iron.getProduct().getPrice() < config.getPrice() :
                        true)
                .filter(iron -> config.getPower()!=null && config.getPower()>0? iron.getPower()< config.getPower():true)
                .filter(iron -> config.isVapourSelected() == false? true: iron.getIsVapor().equals(config.isVapourValue()))
                .collect(Collectors.toList());

        collected.forEach(o -> {
            ProductDTO ironDTO = new ProductDTO();
            ironDTO.setId(o.getId());
            ironDTO.setType("iron");
            ironDTO.setPrice(o.getProduct().getPrice());
            ironDTO.setName(o.getProduct().getName());
            productsToReturn.add(ironDTO);
        });
        return productsToReturn;

    }


   /* @RequestMapping(value = "/getAllProducts", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductDTO> getAllProduct(FilterConfiguration configuration) {
        List<ProductDTO> productsToReturn = new ArrayList<>();

        Iterable<Iron> ironsIterable = searchService.getIrons(configuration.getIronConfig());
        List<Iron> ironsList = new ArrayList<>();
        ironsIterable.forEach(ironsList::add);
        List<Iron> collected = ironsList.stream()
                .filter(iron ->
                        configuration.getName() != null ?
                                iron.getProduct().getName()
                                        .contains(configuration.getName()) :
                                true
                                        && configuration.getPrice() != null ?
                                        iron.getProduct().getPrice() < configuration.getPrice() :
                                        true
                )
                .collect(Collectors.toList());

        collected.forEach(o -> {
            ProductDTO ironDTO = new ProductDTO();
            ironDTO.setId(o.getId());
            ironDTO.setType("iron");
            ironDTO.setPrice(o.getProduct().getPrice());
            ironDTO.setName(o.getProduct().getName());
            productsToReturn.add(ironDTO);
        });
        return productsToReturn;
    }*/

    @RequestMapping(value = "/getAllByName", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductDTO> getAllByName(String name) {
        Iterable<Iron> irons = searchService.getIrons();
        Iterable<SewingMachine> machines = searchService.getMachines();
        Iterable<Furniture> furnitures = searchService.getFurnitures();
        List<ProductDTO> all = new ArrayList<>();
        irons.forEach(o -> {
            ProductDTO ironDTO = new ProductDTO();
            ironDTO.setId(o.getId());
            ironDTO.setPrice(o.getProduct().getPrice());
            ironDTO.setName(o.getProduct().getName());
            ironDTO.setType("iron");
            all.add(ironDTO);
        });
        machines.forEach(o -> {
            ProductDTO machineDTO = new ProductDTO();
            machineDTO.setId(o.getId());
            machineDTO.setPrice(o.getProduct().getPrice());
            machineDTO.setName(o.getProduct().getName());
            machineDTO.setType("machine");
            all.add(machineDTO);
        });
        furnitures.forEach(o -> {
            ProductDTO furniture = new ProductDTO();
            furniture.setId(o.getId());
            furniture.setPrice(o.getProduct().getPrice());
            furniture.setType(o.getType().getType());
            furniture.setName("furniture");
            all.add(furniture);
        });
        List<ProductDTO> collect = all.stream().filter(productDTO -> productDTO.getName().contains(name)).collect(Collectors.toList());
        return collect;
    }


    @RequestMapping(value = "/iron/{id}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public IronInfo getIronInfo(@PathVariable Integer id, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        System.out.println(ip);
        System.out.println(request.getLocalAddr());
        Iron iron = searchService.getIronBy(id);
        IronInfo info = new IronInfo();
        List<Review> allReviews = reviewService.getAllReviews("iron", id);
        info.setIron(iron);
        info.setReviews(
                allReviews
                        .stream()
                        .flatMap(review -> review.getReviews()!=null? review.getReviews().stream(): Stream.empty())
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()));
        View view = new View();
        view.setIp(ip);
        view.setRef_product(iron.getProduct().getId());
        view.setDate(new Date());
        repository.save(view);
        return (info);
    }

    /*@RequestMapping(value = "product/{productType}/{productId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Info getProductInfo(@PathVariable String productType,
                               @PathVariable Integer productId, HttpServletRequest request) throws IOException {

        String ip = request.getRemoteAddr();
        System.out.println(ip);
        System.out.println(request.getLocalAddr());
        if (productType.equals("iron")) {
            Iron iron = searchService.getIronBy(productId);
            IronInfo info = new IronInfo();
            List<Review> allReviews = reviewService.getAllReviews(productType, productId);
            info.setIron(iron);
            info.setReviews(
                    allReviews
                            .stream()
                            .map(review -> {
                                ReviewDTO reviewDTO = new ReviewDTO();
                                reviewDTO.setBody(review.getBody());
                                reviewDTO.setUserName(review.getUser());
                                reviewDTO.setTime(review.getTime());
                                return reviewDTO;
                            })
                            .collect(Collectors.toList()));
            View view = new View();
            view.setIp(ip);
            view.setRef_product(iron.getProduct().getId());
            view.setDate(new Date());
            repository.save(view);
            return (info);

        } else if (productType.equals("furniture")) {

        } else if (productType.equals("machine")) {

        }
//        response.sendRedirect("/info");
        return (new Info());
    }*/
}
