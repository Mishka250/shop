package sh.shop.addd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sh.shop.addd.entity.User;
import sh.shop.addd.repository.UserRepository;

@Service
public class AuthorizationService {
    @Autowired
    UserRepository userRepository;

    public User getUserBy(String email, String password){
        return  userRepository.getByEmailAndPassword(email,password);
    }
    public User getUserBy(Integer id){
        return userRepository.findOne(id);
    }
    public User getUserBy(String username){
        return userRepository.getByUsername(username);
    }
}
