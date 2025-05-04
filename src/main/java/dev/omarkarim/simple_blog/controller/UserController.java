package dev.omarkarim.simple_blog.controller;

import dev.omarkarim.simple_blog.model.User;
import dev.omarkarim.simple_blog.service.PostService;
import dev.omarkarim.simple_blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blog/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @GetMapping
    public Object getUsers(@RequestParam(required = false) String apikey, @RequestParam(required = false) String username) {
        if (apikey != null) {
            return userService.getUserById(apikey).orElseThrow(() -> new RuntimeException("User not found with apikey " + apikey));
        }
        if (username != null) {
            return userService.getUserByUsername(username).orElseThrow(() -> new RuntimeException("User not found with username " + username));
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
        User user = userService.getUserById(apikey).orElseThrow(() -> new RuntimeException("User not found with apikey " + apikey));

        postService.getPostsByAuthor(user).forEach(post -> postService.deletePost(post.getId()));

        return userService.deleteUser(apikey);
    }
}