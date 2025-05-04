package dev.omarkarim.simple_blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import dev.omarkarim.simple_blog.service.CommentService;
import dev.omarkarim.simple_blog.model.*;

import java.util.*;

@RestController
@RequestMapping("/api/blog/comments")
public class CommentController {
    
    @Autowired
    private CommentService commentService;

    @GetMapping
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable Long id) {
        return commentService.getCommentById(id)
                .orElseThrow(() -> new NoSuchElementException("Comment with id " + id + " not found"));
    }

    @GetMapping("/user/{username}")
    public List<Comment> getCommentsByUser(@PathVariable String username) {
        return commentService.getCommentsByAuthor(username);
    }

    @GetMapping("/post/{postId}")
    public List<Comment> getCommentsByPost(@PathVariable Long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @PostMapping("/{apiKey}/{postId}")
    public Comment createComment(@PathVariable String apiKey, @PathVariable Long postId, @RequestBody Comment comment) {
        return commentService.createComment(apiKey, postId, comment)
                .orElseThrow(() -> new NoSuchElementException("Failed to create comment for postId " + postId));
    }

    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        return commentService.updateComment(id, comment);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }
}
