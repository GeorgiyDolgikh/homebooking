package com.dga.homebooking.services;

import com.dga.homebooking.models.User;
import com.dga.homebooking.models.enums.Role;
import com.dga.homebooking.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public boolean createUser(User user){
        String email=user.getEmail();
        if(userRepository.findByEmail(email)!=null) return false;
        user.setActive(true);
        user.getRoles().add(Role.ROLE_USER);
        userRepository.save(user);
        return true;
    }
    public User getUserByEvail(String email){return userRepository.findByEmail(email);}
}
