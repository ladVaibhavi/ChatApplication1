package com.example.jdbidemo.contoller;


import com.example.jdbidemo.domains.User;
import com.example.jdbidemo.domains.UserDTO;
import com.example.jdbidemo.persistant.UserMapper;
import com.example.jdbidemo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@EnableAutoConfiguration

public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService,UserMapper userMapper) {
        this.userService = userService;
        this.userMapper =userMapper;
    }

    // Endpoint to get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }



    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable String id) {
        UserDTO userDTO = userService.findById(id);
         User user= userMapper.userDTOtoUser(userDTO);
        return userDTO;
    }


    @PostMapping(value="/register", produces = "application/json")
//    @Operation(summary = "Get All Notifications Count")
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
}
