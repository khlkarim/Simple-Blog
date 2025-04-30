package dev.omarkarim.simple_blog.controller;

import dev.omarkarim.simple_blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import dev.omarkarim.simple_blog.service.PostService;
import dev.omarkarim.simple_blog.model.Post;

@RestController
@RequestMapping("/api/blog/posts")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

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

    @GetMapping("/tags/{tags}")
    public List<Post> getPostsByTags(@PathVariable List<String> tags) {
        return postService.getPostsByTags(tags);
    }

    @GetMapping("/author/{author}")
    public List<Post> getPostsByAuthor(@PathVariable String author) {
        return postService.getPostsByAuthor(author);
    }

    @GetMapping("/title/{title}")
    public List<Post> getPostsByTitle(@PathVariable String title) {
        return postService.getPostsByTitle(title);
    }

    @PostMapping("/{apikey}")
    public ResponseEntity<Post> createPost(@PathVariable String apikey, @RequestBody Post post) {
        if (userService.getUserById(apikey).isPresent()) {
            post.setAuthor(userService.getUserById(apikey).get().getUsername());
            Post createdPost = postService.createPost(post);
            return ResponseEntity.ok(createdPost);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{apikey}/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable String apikey, @PathVariable Long id, @RequestBody Post postDetails) {
        var userOptional = userService.getUserById(apikey);
        if (userOptional.isPresent()) {
            var postOptional = postService.getPostById(id);
            if (postOptional.isPresent()) {
                String usernameOfKey = userOptional.get().getUsername();
                String usernameOfPost = postOptional.get().getAuthor();
                if (usernameOfKey.equals(usernameOfPost)) {
                    Post updatedPost = postService.updatePost(id, postDetails);
                    return ResponseEntity.ok(updatedPost);
                }
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{apikey}/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable String apikey, @PathVariable Long id) {
        var optionalUser = userService.getUserById(apikey);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var optionalPost = postService.getPostById(id);
        if (optionalPost.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        String usernameOfKey = optionalUser.get().getUsername();
        String usernameOfPost = optionalPost.get().getAuthor();
        if (!usernameOfKey.equals(usernameOfPost)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
