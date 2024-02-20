package com.dga.homebooking.controllers;

import com.dga.homebooking.models.Filial;
import com.dga.homebooking.models.User;
import com.dga.homebooking.repo.FilialRepository;
import com.dga.homebooking.repo.UserRepository;
import com.dga.homebooking.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserController {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    FilialRepository filialRepository;
    @Autowired
    UserService userService;

    @GetMapping("/registration")
    String getRegistration(Model model){
        List<Filial> filialList=new ArrayList<>();
        filialRepository.findAll().forEach(filialList::add);
        model.addAttribute("filiallist",filialList);
        return "registration";
    }
    @PostMapping("/registration")
    String createUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.createUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
