package dev.omarkarim.simple_blog.data;

import dev.omarkarim.simple_blog.model.Post;
import dev.omarkarim.simple_blog.model.User;
import dev.omarkarim.simple_blog.repository.PostRepository;
import dev.omarkarim.simple_blog.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.List;
import java.time.LocalDate;

@Component
@DependsOn("userDataLoader")
public class PostDataLoader {
    private UserRepository userRepository;
    private PostRepository postRepository;

    public PostDataLoader(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @PostConstruct
    public void loadPostData() {
        if (postRepository.count() == 0) {
            List <User> users = userRepository.findAll();

            List<Post> posts = List.of(
                new Post("Understanding REST APIs", "REST APIs are an architectural style for building scalable web services. This post explains their key principles.", LocalDate.now().minusDays(30), List.of("REST", "API design"), users.get(0)),
                new Post("Introduction to GraphQL", "GraphQL is a query language for APIs and a runtime for executing those queries. Learn how it differs from REST.", LocalDate.now().minusDays(25), List.of("GraphQL", "API"), users.get(1)),
                new Post("Best Practices in API Security", "API security is critical. Explore techniques like authentication, authorization, and encryption.", LocalDate.now().minusDays(20), List.of("Security", "API"), users.get(2)),
                new Post("Versioning in APIs", "API versioning is vital for ensuring backward compatibility. Learn the best strategies for versioning.", LocalDate.now().minusDays(15), List.of("API", "Versioning"), users.get(3)),
                new Post("Building APIs with Spring Boot", "Spring Boot provides a powerful framework for quickly building robust APIs. Here’s how to get started.", LocalDate.now().minusDays(10), List.of("Spring Boot", "API development"), users.get(0)),
                new Post("Understanding Rate Limiting in APIs", "Rate limiting helps control traffic and prevent misuse. Learn how to implement it effectively.", LocalDate.now().minusDays(9), List.of("Rate Limiting", "API Security"), users.get(1)),
                new Post("Debugging HTTP Status Codes", "Ever wondered what those HTTP 4xx or 5xx errors mean? Here’s a detailed explanation.", LocalDate.now().minusDays(8), List.of("HTTP", "Debugging"), users.get(2)),
                new Post("The Role of API Gateways", "API gateways are vital for managing and securing microservices. Let’s dive into how they work.", LocalDate.now().minusDays(7), List.of("API Gateway", "Microservices"), users.get(3)),
                new Post("Caching Strategies for APIs", "Caching can dramatically improve API performance. Learn the different techniques and tools.", LocalDate.now().minusDays(6), List.of("Caching", "Performance"), users.get(0)),
                new Post("Introduction to OpenAPI (Swagger)", "OpenAPI and Swagger are powerful tools for designing and documenting APIs. Learn the basics here.", LocalDate.now().minusDays(5), List.of("OpenAPI", "Swagger"), users.get(1)),
                new Post("Securing APIs with OAuth2", "OAuth2 is the industry standard for access delegation. Learn how it can secure your APIs.", LocalDate.now().minusDays(4), List.of("OAuth2", "Security"), users.get(2)),
                new Post("Handling Errors in APIs", "Error handling is critical for user experience. Learn the best practices for managing and documenting errors.", LocalDate.now().minusDays(3), List.of("Error Handling", "API Design"), users.get(3)),
                new Post("Testing APIs with Postman", "Postman is a popular tool for API testing. Here’s how you can use it to quickly test APIs.", LocalDate.now().minusDays(2), List.of("Postman", "Testing"), users.get(0)),
                new Post("Introduction to WebSockets", "WebSockets enable real-time, full-duplex communication. Understand how they work and how to implement them.", LocalDate.now().minusDays(1), List.of("WebSockets", "Real-time"), users.get(1)),
                new Post("GraphQL vs REST: Pros and Cons", "Should you use GraphQL or REST for your next API? Learn their advantages and challenges.", LocalDate.now(), List.of("GraphQL", "REST"), users.get(2)),
                new Post("API Monitoring and Analytics", "Monitoring is key to understanding API performance and usage. Discover tools and strategies to track APIs.", LocalDate.now(), List.of("Monitoring", "Analytics"), users.get(3))
            );
            postRepository.saveAll(posts);
        }
    }
}