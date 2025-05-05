package dev.omarkarim.simple_blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import dev.omarkarim.simple_blog.model.*;
import dev.omarkarim.simple_blog.service.*;

@RestController
@RequestMapping("/api/blog/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public Object getUsers(@RequestParam(required = false) String apikey, @RequestParam(required = false) String username) {
        if (apikey != null) {
            return userService.getUserById(apikey);
        }
        if (username != null) {
            return userService.getUserByUsername(username);
        }

        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PatchMapping
    public User updateUser(@RequestParam String apikey, @RequestBody User updatedUser) {
        return userService.updateUser(apikey, updatedUser);
    }

    @DeleteMapping
    public boolean deleteUser(@RequestParam String apikey) {
        return userService.deleteUser(apikey);
    }
}