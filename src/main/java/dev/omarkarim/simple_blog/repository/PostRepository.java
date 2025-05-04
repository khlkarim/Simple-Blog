package dev.omarkarim.simple_blog.repository;

import dev.omarkarim.simple_blog.model.Post;
import dev.omarkarim.simple_blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTagsContaining(String tag);
    List<Post> findByTitle(String title);
    List<Post> findByTitleContainingIgnoreCase(String keyword);
    List<Post> findByAuthor(User author);
}
