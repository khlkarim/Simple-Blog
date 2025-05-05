package dev.omarkarim.simple_blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dev.omarkarim.simple_blog.exception.*;
import dev.omarkarim.simple_blog.model.*;
import dev.omarkarim.simple_blog.repository.*;

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
                .orElseThrow(() -> new CommentNotFoundException("Comment not found with ID: " + id));
    }

    public List<Comment> getCommentsByAuthor(String username) {
        User author = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        return commentRepository.findByAuthor(author);
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found with ID: " + postId));
        return commentRepository.findByPost(post);
    }

    public Comment createComment(String apikey, Long postId, Comment comment) {
        User author = userRepository.findById(apikey)
                .orElseThrow(() -> new UserNotFoundException("Authenticated user not found with API key: " + apikey));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found with ID: " + postId));

        comment.setAuthor(author);
        comment.setPost(post);
        return commentRepository.save(comment);
    }

    public Comment updateComment(Long id, String apikey, Comment updatedComment) {
        if (!isAuthorizedUser(apikey, id)) {
            throw new UnauthorizedActionException("You are not authorized to update this comment.");
        }

        Comment currComment = commentRepository.findById(id).orElseThrow(
                () -> new CommentNotFoundException("Comment not found with ID: " + id)
        );

        updatedComment.setAuthor(userRepository.findUserByUsername(currComment.getAuthor()).orElseThrow(
                () -> new UserNotFoundException("User not found with username: " + currComment.getAuthor())
        ));
        updatedComment.setPost(postRepository.findById(currComment.getPost()).orElseThrow(
                () -> new PostNotFoundException("Post not found with ID: " + currComment.getPost())
        ));

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found with ID: " + id));

        comment.setContent(updatedComment.getContent());

        if (updatedComment.getPost() != null) {
            Post post = postRepository.findById(updatedComment.getPost())
                    .orElseThrow(() -> new PostNotFoundException("Post not found with ID: " + updatedComment.getPost()));
            comment.setPost(post);
        }

        User author = userRepository.findById(apikey)
                .orElseThrow(() -> new UserNotFoundException("Authenticated user not found with API key: " + apikey));
        comment.setAuthor(author);

        return commentRepository.save(comment);
    }

    public void deleteComment(Long id, String apikey) {
        if (!isAuthorizedUser(apikey, id)) {
            throw new UnauthorizedActionException("You are not authorized to delete this comment.");
        }

        if (!commentRepository.existsById(id)) {
            throw new CommentNotFoundException("Comment not found with ID: " + id);
        }
        commentRepository.deleteById(id);
    }

    private boolean isAuthorizedUser(String apikey, Long commentId) {
        User user = userRepository.findById(apikey)
                .orElseThrow(() -> new UserNotFoundException("Authenticated user not found with API key: " + apikey));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found with ID: " + commentId));

        return user.getUsername().equals(comment.getAuthor());
    }
}