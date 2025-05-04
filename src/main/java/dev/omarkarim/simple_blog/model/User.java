package dev.omarkarim.simple_blog.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "api_key")
    private String key;

    private String username;
    private String fullName;
    private String email;
    private String numTel;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public User(){

    }

    public User(String username, String fullName, String email, String numTel) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.numTel = numTel;
    }

    public String getKey() {
        return key;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public List<Post> getPosts() {
        return Collections.unmodifiableList(posts);
    }

    public void addPost(Post post) {
        posts.add(post);
        post.setAuthor(this);
    }

    public void removePost(Post post) {
        posts.remove(post);
        post.setAuthor(null);
    }

    public List<Comment> getComments() {
        return Collections.unmodifiableList(comments);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setAuthor(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setAuthor(null);
    }
}
