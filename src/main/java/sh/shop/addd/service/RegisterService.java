package sh.shop.addd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sh.shop.addd.entity.User;
import sh.shop.addd.repository.UserRepository;

@Service
public class RegisterService {
    @Autowired
    private UserRepository userRepository;

    public Integer register(String username, String email, String password, String phone){
        User user = userRepository.getByEmailAndPassword(email, password);
        if(user != null){
            return -1;
        }
        user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);
        userRepository.save(user);
        return user.getId();
    }
}
