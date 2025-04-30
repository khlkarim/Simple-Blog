package dev.omarkarim.simple_blog.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "api_key", updatable = false)
    private String key;
    @Column(unique = true)
    private String username; // La colonne reliant les Users et les Posts
    private String fullName;
    private String email;
    private String numTel;

    public User() {
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
    public void hideKey() {
        this.key = null;
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
}