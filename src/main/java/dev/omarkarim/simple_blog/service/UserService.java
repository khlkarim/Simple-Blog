package dev.omarkarim.simple_blog.service;

import dev.omarkarim.simple_blog.model.User;
import dev.omarkarim.simple_blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dev.omarkarim.simple_blog.exception.UserNotFoundException;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username " + username));
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + id));
    }

    public User updateUser(String id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            if (isValid(updatedUser.getFullName())) {
                user.setFullName(updatedUser.getFullName());
            }
            if (isValid(updatedUser.getEmail())) {
                user.setEmail(updatedUser.getEmail());
            }
            if (isValid(updatedUser.getNumTel())) {
                user.setNumTel(updatedUser.getNumTel());
            }
            return userRepository.save(user);
        }).orElseThrow(() -> new UserNotFoundException("User not found with id " + id));
    }

    private boolean isValid(String value) {
        return value != null && !value.isEmpty();
    }

    public boolean deleteUser(String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {
            throw new UserNotFoundException("User not found with id " + id);
        }
    }
}
