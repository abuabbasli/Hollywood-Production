package com.example.hollywoodProduction.Services;

import com.example.hollywoodProduction.Entity.User;
import java.util.List;

import com.example.hollywoodProduction.Entity.Video;
import org.springframework.stereotype.Service;
public interface UserService {
     User addUser(User user);
     User updateUser(Long id, User userDetails);

     void deleteUser(Long id);

     User getUserById(Long id);
     List<User> getAllUsers();


}
