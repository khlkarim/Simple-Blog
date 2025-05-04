package dev.omarkarim.simple_blog.controller;

import dev.omarkarim.simple_blog.model.User;
import dev.omarkarim.simple_blog.service.PostService;
import dev.omarkarim.simple_blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blog/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{apikey}")
    public User getUserByKey(@PathVariable String apikey) {
        return userService.getUserById(apikey).orElseThrow(() -> new RuntimeException("User not found with apikey " + apikey));
    }

    @GetMapping("/")
    public User getUserByUsername(@RequestParam String username) {
        return userService.getUserByUsername(username).orElseThrow(() -> new RuntimeException("User not found with username " + username));
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PatchMapping("/{apikey}")
    public User updateUser(@PathVariable String apikey, @RequestBody User updatedUser) {
        return userService.updateUser(apikey, updatedUser);
    }

    @DeleteMapping("/{apikey}")
    public boolean deleteUser(@PathVariable String apikey) {
        User username = userService.getUserById(apikey).get();

        postService.getPostsByAuthor(username).forEach(post -> postService.deletePost(post.getId()));

        return userService.deleteUser(apikey);
    }
}