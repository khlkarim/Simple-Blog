package dev.omarkarim.simple_blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.omarkarim.simple_blog.model.*;
import dev.omarkarim.simple_blog.repository.*;
import dev.omarkarim.simple_blog.exception.*;

@Service
public class PostService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id " + id));
    }

    public List<Post> getPostsByAuthor(User author) {
        return postRepository.findByAuthor(author);
    }

    public List<Post> getPostsByTitle(String title) {
        return postRepository.findByTitle(title);
    }

    public List<Post> getPostsByTags(List<String> tags) {
        return postRepository.findAll().stream()
                .filter(post -> tags.stream().allMatch(tag -> post.getTags().contains(tag)))
                .toList();
    }

    public List<Post> filterPosts(List<String> tags, String author, String title) {
        List<Post> filteredPosts = getAllPosts();

        if (tags != null && !tags.isEmpty()) {
            filteredPosts = getPostsByTags(tags);
        }

        if (author != null && !author.isEmpty()) {
            User user = userRepository.findUserByUsername(author)
                    .orElseThrow(() -> new UserNotFoundException("User not found with username " + author));
            filteredPosts.retainAll(getPostsByAuthor(user));
        }

        if (title != null && !title.isEmpty()) {
            filteredPosts.retainAll(getPostsByTitle(title));
        }

        return filteredPosts;
    }

    public Post createPost(String apikey, Post post) {
        User user = userRepository.findById(apikey)
                .orElseThrow(() -> new UserNotFoundException("User not found with API key " + apikey));
        post.setAuthor(user);
        return postRepository.save(post);
    }

    public Post updatePost(String apikey, Long id, Post updatedPost) {
        if (!isAuthorizedUser(apikey, id)) {
            throw new UnauthorizedActionException("User is not authorized to update this post");
        }

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id " + id));

        if (isValid(updatedPost.getTitle())) {
            post.setTitle(updatedPost.getTitle());
        }
        if (isValid(updatedPost.getContent())) {
            post.setContent(updatedPost.getContent());
        }
        if (updatedPost.getTags() != null && !updatedPost.getTags().isEmpty()) {
            post.setTags(updatedPost.getTags());
        }
        return postRepository.save(post);
    }

    private boolean isValid(String value) {
        return value != null && !value.isEmpty();
    }

    public boolean deletePost(String apikey, Long id) {
        if (!isAuthorizedUser(apikey, id)) {
            throw new UnauthorizedActionException("User is not authorized to delete this post");
        }

        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return true;
        }
        throw new PostNotFoundException("Post not found with id " + id);
    }

    private boolean isAuthorizedUser(String apikey, Long postId) {
        User user = userRepository.findById(apikey)
                .orElseThrow(() -> new UserNotFoundException("User not found with API key " + apikey));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id " + postId));

        return user.getUsername().equals(post.getAuthor());
    }
}