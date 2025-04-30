package dev.omarkarim.simple_blog.service;

import dev.omarkarim.simple_blog.model.User;
import dev.omarkarim.simple_blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        ArrayList <User> users = new ArrayList<>(userRepository.findAll());

        for (User user : users) {
            user.hideKey();
        }

        return users;
    }

    public Optional<User> getUserByUsername(String username) {
        Optional <User> u = userRepository.findUserByUsername(username);

        if (u.isPresent()) {
            u.get().hideKey();
        }

        return u;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public User updateUser(String id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            if (updatedUser.getFullName() != null && !updatedUser.getFullName().isEmpty()) {
                user.setFullName(updatedUser.getFullName());
            }
            if (updatedUser.getEmail() != null && !updatedUser.getEmail().isEmpty()) {
                user.setEmail(updatedUser.getEmail());
            }
            if (updatedUser.getNumTel() != null && !updatedUser.getNumTel().isEmpty()) {
                user.setNumTel(updatedUser.getNumTel());
            }
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    public boolean deleteUser(String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
