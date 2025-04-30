package dev.omarkarim.simple_blog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.omarkarim.simple_blog.model.Post;
import dev.omarkarim.simple_blog.repository.PostRepository;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    public List<Post> getPostsByAuthor(String author) {
        return postRepository.findByAuthor(author);
    }

    public List<Post> getPostsByTitle(String title) {
        return postRepository.findByTitle(title);
    }

    public List<Post> getPostsByTags(List<String> tags) {
        return postRepository.findByTags(tags, tags.size());
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Post updatePost(Long id, Post updatedPost) {
        return postRepository.findById(id).map(post -> {
            if (updatedPost.getTitle() != null && !updatedPost.getTitle().isEmpty()) {
                post.setTitle(updatedPost.getTitle());
            }
            if (updatedPost.getContent() != null && !updatedPost.getContent().isEmpty()) {
                post.setContent(updatedPost.getContent());
            }
            if (updatedPost.getTags() != null && !updatedPost.getTags().isEmpty()) {
                post.setTags(updatedPost.getTags());
            }
            return postRepository.save(post);
        }).orElseThrow(() -> new RuntimeException("Post not found with id " + id));
    }

    public boolean deletePost(Long id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }
}