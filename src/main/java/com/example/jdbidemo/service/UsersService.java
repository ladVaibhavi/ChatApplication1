package com.example.jdbidemo.service;

import com.example.jdbidemo.domains.User;
import com.example.jdbidemo.persistant.UserMapper;
import com.example.jdbidemo.persistant.UserRepositoryImpl;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class UsersService implements UserDetailsService {
    private final UserRepositoryImpl userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UsersService(UserRepositoryImpl userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public String hash(String value) {
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

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void validateUser(User user) throws Exception
    {
        if (StringUtils.isEmpty(user.getEmail()) || StringUtils.isEmpty(user.getFirstname())) {
            throw new Error("Name and Email are mandatory.");
        }
    }
    public User createUser(User user) throws Exception {
        validateUser(user);
        user.setId(hash(user.getEmail()+user.getFirstname()));
        user.setPassword(hash(user.getPassword()));
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


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByEmail(username);
    }
}
