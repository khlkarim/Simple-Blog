package dev.omarkarim.simple_blog.model;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private LocalDate date;

    @ElementCollection
    private List<String> tags;

    @ManyToOne(optional = false)
    private User author;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    public Post(){

    }

    public Post(String title, String content, LocalDate date, List<String> tags, User author) {
        this.id = null;
        this.title = title;
        this.content = content;
        this.date = date != null ? date : LocalDate.now();
        this.tags = tags != null ? List.copyOf(tags) : Collections.emptyList();
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date != null ? date : LocalDate.now();
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags != null ? List.copyOf(tags) : Collections.emptyList();
    }

    public String getAuthor() {
        return author.getUsername();
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
