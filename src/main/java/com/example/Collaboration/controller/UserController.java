package com.example.Collaboration.controller;

import com.example.Collaboration.dto.ReqRes;
import com.example.Collaboration.entity.OurUsers;
import com.example.Collaboration.service.AuthService;
import com.example.Collaboration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")

    public ReqRes getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/delete/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")

    public ReqRes deleteUser(@PathVariable Integer userId) {
        return userService.deleteUser(userId);
    }

    @PutMapping("/update/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")

    public ReqRes updateUser(@PathVariable Integer userId, @RequestBody OurUsers updatedUser) {
        return userService.updateUser(userId, updatedUser);
    }


}
