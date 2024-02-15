package com.example.jdbidemo.persistant;

import com.example.jdbidemo.domains.UserDTO;
import com.example.jdbidemo.domains.User;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO userToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstname(user.getFirstname());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    public User userDTOtoUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setEmail(userDTO.getEmail());
        return user;
    }
}
