package dev.omarkarim.simple_blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import dev.omarkarim.simple_blog.service.CommentService;
import dev.omarkarim.simple_blog.model.*;

import java.util.*;

@RestController
@RequestMapping("/api/blog/comments")
@CrossOrigin(origins = "*")
public class CommentController {
    
    @Autowired
    private CommentService commentService;

    @GetMapping
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable Long id) {
        return commentService.getCommentById(id);
    }

    @GetMapping("/user/{username}")
    public List<Comment> getCommentsByUser(@PathVariable String username) {
        return commentService.getCommentsByAuthor(username);
    }

    @GetMapping("/post/{postId}")
    public List<Comment> getCommentsByPost(@PathVariable Long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @PostMapping("/{postId}")
    public Comment createComment(@RequestParam String apikey, @PathVariable Long postId, @RequestBody Comment comment) {
        return commentService.createComment(apikey, postId, comment);
    }

    @PatchMapping("/{id}")
    public Comment updateComment(@RequestParam String apikey, @PathVariable Long id, @RequestBody Comment comment) {
        return commentService.updateComment(apikey, id, comment);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@RequestParam String apikey, @PathVariable Long id) {
        commentService.deleteComment(apikey, id);
    }
}
