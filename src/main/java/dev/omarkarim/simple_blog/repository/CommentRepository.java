package dev.omarkarim.simple_blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.omarkarim.simple_blog.model.Comment;
import dev.omarkarim.simple_blog.model.User;
import dev.omarkarim.simple_blog.model.Post;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByAuthor(User author);
    List<Comment> findByPost(Post post);

    Post getCommentById(Long id);
}
