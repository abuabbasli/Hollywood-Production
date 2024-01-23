package com.example.hollywoodProduction.ServiceImpl;

import com.example.hollywoodProduction.Entity.User;
import com.example.hollywoodProduction.Repository.UserRepository;
import com.example.hollywoodProduction.Services.UserService;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public User addUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User parameter is null");
        }
        logger.info("Adding user: {}", user.getName());
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User userDetails) {
        if (userDetails == null) {
            throw new IllegalArgumentException("User details parameter is null");
        }

        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            logger.info("Updating user with id {}: {}", id, user.getName());
            return userRepository.save(user);
        } else {
            logger.error("User not found with id: {}", id);
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    @Override
    public void deleteUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            logger.info("Deleting user with id {}: {}", id, user.getName());
            userRepository.delete(user);
        } else {
            logger.error("User not found with id: {}", id);
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            logger.error("User not found with id: {}", id);
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        logger.info("Retrieved {} users", users.size());
        return users;
    }
}