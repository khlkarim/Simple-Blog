package dev.omarkarim.simple_blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import dev.omarkarim.simple_blog.service.*;
import dev.omarkarim.simple_blog.model.*;

@RestController
@RequestMapping("/api/blog/posts")
@CrossOrigin(origins = "*")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    @GetMapping("/filter")
    public List<Post> filterPosts(
            @RequestParam(required = false) List<String> tags,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String title) {

        return postService.filterPosts(tags, author, title);
    }

    @PostMapping
    public Post createPost(@RequestParam String apikey, @RequestBody Post post) {
        return postService.createPost(apikey, post);
    }

    @PatchMapping("/{id}")
    public Post updatePost(@RequestParam String apikey, @PathVariable Long id, @RequestBody Post postDetails) {
        return postService.updatePost(apikey, id, postDetails);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@RequestParam String apikey, @PathVariable Long id) {
        postService.deletePost(apikey, id);
    }
}
