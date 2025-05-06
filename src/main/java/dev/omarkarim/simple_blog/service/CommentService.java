package dev.omarkarim.simple_blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.omarkarim.simple_blog.model.*;
import dev.omarkarim.simple_blog.repository.*;
import dev.omarkarim.simple_blog.exception.*;

import java.util.List;

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

    public Comment getCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));
    }

    public List<Comment> getCommentsByAuthor(String author) {
        User user = userRepository.findUserByUsername(author)
                .orElseThrow(() -> new UserNotFoundException("Author not found"));
        return commentRepository.findByAuthor(user);
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));
        return commentRepository.findByPost(post);
    }

    public Comment createComment(String apikey, Long postId, Comment comment) {
        User user = userRepository.findById(apikey)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));

        comment.setAuthor(user);
        comment.setPost(post);
        return commentRepository.save(comment);
    }

    public Comment updateComment(String apikey, Long id, Comment updatedComment) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));

        User user = userRepository.findById(apikey)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!comment.getAuthor().equals(user.getUsername())) {
            throw new UnauthorizedActionException("User does not own this comment");
        }

        comment.setContent(updatedComment.getContent());

        return commentRepository.save(comment);
    }

    public void deleteComment(String apikey, Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));

        User user = userRepository.findById(apikey)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!comment.getAuthor().equals(user.getUsername())) {
            throw new UnauthorizedActionException("User does not own this comment");
        }

        commentRepository.deleteById(id);
    }
}
