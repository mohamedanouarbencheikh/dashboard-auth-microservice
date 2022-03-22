package com.github.loginservice.controller;

import com.github.loginservice.business.UserBusiness;
import com.github.loginservice.models.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Mohamed Anouar BENCHEIKH
 * @project login-service
 */
@RestController
public class AdminController {

    @Autowired
    UserBusiness userBusiness;

    @PostMapping(value = "/app/addUser")
    public void addUser(@RequestBody UserDb userDb) {
        userDb.setRoleUser(userBusiness.addRoleDb(userDb.getRoleUser()));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userDb.setPassword(passwordEncoder.encode(userDb.getPassword()));
        userBusiness.addUserDb(userDb);
    }

    @GetMapping(value = "/app/getUsers")
    public List<UserDb> getUsers() {
        return userBusiness.getUsers();
    }
}
