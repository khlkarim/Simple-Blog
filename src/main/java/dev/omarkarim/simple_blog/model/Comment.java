package dev.omarkarim.simple_blog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="comments")
public class Comment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;

    @ManyToOne
    private Post post;

    @ManyToOne
    private User author;

    public Comment(){

    }

    public Comment(String content, Post post, User author) {
        this.content = content;
        this.post = post;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getPost() {
        return post.getId();
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getAuthor() {
        return author.getUsername();
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
