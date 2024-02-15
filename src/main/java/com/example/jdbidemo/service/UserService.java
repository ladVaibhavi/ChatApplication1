package com.example.jdbidemo.service;

import com.example.jdbidemo.domains.User;
import com.example.jdbidemo.domains.UserDTO;
import com.example.jdbidemo.persistant.UserMapper;
import com.example.jdbidemo.persistant.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class UserService {
    private final UserRepositoryImpl userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepositoryImpl userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }
    public String hash(String value)
    {
        try {
            return DigestUtils.md5DigestAsHex(value.getBytes(StandardCharsets.UTF_8));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<User> getAllUsers() {

        return userRepository.getAllUsers();
    }

    public UserDTO findById(String id) {
        User user = userRepository.findById(id);
        if (user != null) {
            return userMapper.userToUserDTO(user);
        }
        return null;
    }

    public User createUser(User user) {
        user.setId(hash(user.getEmail()+user.getFirstname()));
        User user1 = userRepository.addNewUser(user);

        return user1;
    }

    public User updateUser(Long id, User updatedUser) {
        User user1 = userRepository.updateUser(id,updatedUser);
        return updatedUser;
    }

    public void deleteUser(Long id) {
        userRepository.deleteUser(id);
    }


 
}
