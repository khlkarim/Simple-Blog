package dev.omarkarim.simple_blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import dev.omarkarim.simple_blog.model.Post;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p JOIN p.tags t WHERE t IN :tags GROUP BY p HAVING COUNT(DISTINCT t) = :tagCount")
    List<Post> findByTags(@Param("tags") List<String> tags, @Param("tagCount") long tagCount);

    @Query("SELECT p FROM Post p WHERE p.title = :title")
    List<Post> findByTitle(@Param("title") String title);

    @Query("SELECT p FROM Post p WHERE p.author = :author")
    List<Post> findByAuthor(@Param("author") String author);
}

