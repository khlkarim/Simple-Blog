package dev.omarkarim.simple_blog.service;

import dev.omarkarim.simple_blog.model.User;
import dev.omarkarim.simple_blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
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
        }).orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    private boolean isValid(String value) {
        return value != null && !value.isEmpty();
    }

    public boolean deleteUser(String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
