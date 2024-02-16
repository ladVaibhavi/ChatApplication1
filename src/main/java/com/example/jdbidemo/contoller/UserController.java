package com.example.jdbidemo.contoller;


import com.example.jdbidemo.domains.User;
import com.example.jdbidemo.persistant.UserMapper;
import com.example.jdbidemo.service.UsersService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@EnableAutoConfiguration

public class UserController {

    private final UsersService userService;
    private final UserMapper userMapper;

    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    public UserController(UsersService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper =userMapper;
    }

    // Endpoint to get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/login")
    public User loadUserByUsername(@RequestBody String email) {
        User user = userService.findByEmail(email);
        return user;
    }

    @PostMapping(value="/register", produces = "application/json")
    public User registerUser(@RequestBody User user) throws Exception {
        try {
            return userService.createUser(user);
        } catch (Exception ex) {
            throw ex;
        }
    }
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User user = userService.updateUser(id, updatedUser);

       return updatedUser;
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id)
    {
        userService.deleteUser(id);
    }

        @RequestMapping("/test")
        public String test() {
            this.logger.warn("This is working message");
            return "Testing message";
        }



}
