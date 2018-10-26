package com.angel.customuserdetailsauthentication.controller;

import com.angel.customuserdetailsauthentication.domain.Role;
import com.angel.customuserdetailsauthentication.domain.User;
import com.angel.customuserdetailsauthentication.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;

@RestController
@Slf4j
public class GeneralController {

    final UserRepository userRepository;

    final PasswordEncoder passwordEncoder;

    @Autowired
    public GeneralController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping("/public")
    public String mpublic() {
        return "This works !";
    }

    @RequestMapping("/confidential")
    public String confidential() {
        return "I mean nothing confidential here ...";
    }

    @PostMapping("/create")
    public void createUser(@RequestBody User user) {
        User toSave = new User(user.getUsername(),
                passwordEncoder.encode(user.getPassword()),
                Collections.singletonList(new Role("ROLE_USER")));
        userRepository.save(toSave);
    }

    @PostMapping("/create/admin")
    public void createAdmin(@RequestBody User user) {

        User toSave = new User(user.getUsername(),
                passwordEncoder.encode(user.getPassword()),
                Arrays.asList(new Role("ROLE_USER"), new Role("ROLE_ADMIN")));
        userRepository.save(toSave);
    }
}
