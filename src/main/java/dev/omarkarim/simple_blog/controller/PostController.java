package dev.omarkarim.simple_blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import dev.omarkarim.simple_blog.service.*;
import dev.omarkarim.simple_blog.model.*;

@RestController
@RequestMapping("/api/blog/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        return postService.getPostById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Post>> filterPosts(
            @RequestParam(required = false) List<String> tags,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String title) {

        List<Post> filteredPosts = postService.filterPosts(tags, author, title);
        if (filteredPosts == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(filteredPosts);
    }

    @PostMapping("/{apikey}")
    public ResponseEntity<Optional<Post>> createPost(@PathVariable String apikey, @RequestBody Post post) {
        Optional<Post> createdPost = postService.createPost(apikey, post);
        
        if (createdPost.isPresent()) {
            return ResponseEntity.ok(createdPost);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{apikey}/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable String apikey, @PathVariable Long id, @RequestBody Post postDetails) {
        if (!postService.isAuthorizedUser(apikey, id)) {
            return ResponseEntity.badRequest().build();
        }

        Post updatedPost = postService.updatePost(id, postDetails);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{apikey}/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable String apikey, @PathVariable Long id) {
        if (!postService.isAuthorizedUser(apikey, id)) {
            return ResponseEntity.badRequest().build();
        }

        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
