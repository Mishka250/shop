package sh.shop.addd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sh.shop.addd.service.RegisterService;

@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Integer register(String username, String email, String password, String phone) {
        return registerService.register(username, email, password, phone);
    }
}
