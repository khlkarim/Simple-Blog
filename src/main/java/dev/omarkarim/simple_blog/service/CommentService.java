package dev.omarkarim.simple_blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.omarkarim.simple_blog.model.*;
import dev.omarkarim.simple_blog.repository.*;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    public List<Comment> getCommentsByAuthor(String author) {
        return userRepository.findUserByUsername(author)
                .map(user -> commentRepository.findByAuthor(user))
                .orElseThrow(() -> new RuntimeException("Author not found"));
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        return postRepository.findById(postId)
                .map(post -> commentRepository.findByPost(post))
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public Optional<Comment> createComment(String apikey, Long postId, Comment comment) {
        Optional<User> user = userRepository.findById(apikey);
        Optional<Post> post = postRepository.findById(postId);

        if (user.isPresent() && post.isPresent()) {
            comment.setAuthor(user.get());
            comment.setPost(post.get());
            return Optional.of(commentRepository.save(comment));
        } else {
            return Optional.empty();
        }
    }

    public Comment updateComment(Long id, Comment updatedComment) {
        return commentRepository.findById(id).map(comment -> {
            comment.setContent(updatedComment.getContent());
            
            User author = userRepository.findUserByUsername(updatedComment.getAuthor())
                    .orElseThrow(() -> new RuntimeException("Author not found"));
            comment.setAuthor(author);
            
            Post post = postRepository.findById(updatedComment.getPost())
                    .orElseThrow(() -> new RuntimeException("Post not found"));
            comment.setPost(post);
            
            return commentRepository.save(comment);
        }).orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
