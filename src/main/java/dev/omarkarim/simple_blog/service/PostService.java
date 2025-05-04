package dev.omarkarim.simple_blog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.omarkarim.simple_blog.model.*;
import dev.omarkarim.simple_blog.repository.*;

@Service
public class PostService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    public List<Post> getPostsByAuthor(User author) {
        return postRepository.findByAuthor(author);
    }

    public List<Post> getPostsByTitle(String title) {
        return postRepository.findByTitle(title);
    }

    public List<Post> getPostsByTags(List<String> tags) {
        return(
            postRepository.findAll().stream()
            .filter(post -> tags.stream().allMatch(tag -> post.getTags().contains(tag)))
            .toList()
        );
    }

    public List<Post> filterPosts(List<String> tags, String author, String title) {
        List<Post> filteredPosts = getAllPosts();

        if (tags != null && !tags.isEmpty()) {
            filteredPosts = getPostsByTags(tags);
        }

        if (author != null && !author.isEmpty()) {
            Optional<User> optionalUser = userRepository.findUserByUsername(author);
            if (optionalUser.isPresent()) {
                filteredPosts.retainAll(getPostsByAuthor(optionalUser.get()));
            } else {
                return null;
            }
        }

        if (title != null && !title.isEmpty()) {
            filteredPosts.retainAll(getPostsByTitle(title));
        }

        return filteredPosts;
    }

    public Optional<Post> createPost(String apikey, Post post) {
        Optional<User> user = userRepository.findById(apikey);

        if(user.isPresent()){
            post.setAuthor(user.get());
            return Optional.of(postRepository.save(post));
        }else{
            return Optional.empty();
        }
    }

    public Post updatePost(Long id, Post updatedPost) {
        return postRepository.findById(id).map(post -> {
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
        }).orElseThrow(() -> new RuntimeException("Post not found with id " + id));
    }

    private boolean isValid(String value) {
        return value != null && !value.isEmpty();
    }

    public boolean deletePost(Long id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean isAuthorizedUser(String apikey, Long postId) {
        var optionalUser = userRepository.findById(apikey);
        if (optionalUser.isEmpty()) {
            return false;
        }

        var optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            return false;
        }

        String usernameOfKey = optionalUser.get().getUsername();
        String usernameOfPost = optionalPost.get().getAuthor();
        return usernameOfKey.equals(usernameOfPost);
    }
}