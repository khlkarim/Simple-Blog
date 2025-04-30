package dev.omarkarim.simple_blog.data;

import dev.omarkarim.simple_blog.model.Post;
import dev.omarkarim.simple_blog.repository.PostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("dev")
public class PostDataLoader implements CommandLineRunner {

    private final PostRepository postRepository;

    public PostDataLoader(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public void run(String... args) {
        if (postRepository.count() == 0) {
            Post post1 = new Post("Spring Boot Tips", List.of("spring", "java", "backend"),
                "Learn how to use Spring Boot effectively in your next project.");
            Post post2 = new Post("Understanding JPA", List.of("jpa", "hibernate", "database"),
                "An introduction to Java Persistence API with real-world examples.");
            Post post3 = new Post("REST API Best Practices", List.of("rest", "api", "best-practices"),
                "Best practices to follow when designing and developing RESTful APIs.");
            Post post4 = new Post("Microservices Architecture", List.of("microservices", "architecture", "cloud"),
                "A guide to building scalable and resilient microservices.");
            Post post5 = new Post("Docker for Beginners", List.of("docker", "containers", "devops"),
                "Learn the basics of Docker and how to containerize your applications.");
            Post post6 = new Post("Kubernetes Essentials", List.of("kubernetes", "orchestration", "cloud"),
                "An introduction to Kubernetes and its core concepts.");
            Post post7 = new Post("Effective Unit Testing", List.of("testing", "junit", "java"),
                "Tips and techniques for writing effective unit tests in Java.");
            Post post8 = new Post("Introduction to GraphQL", List.of("graphql", "api", "backend"),
                "Learn the basics of GraphQL and how it differs from REST.");
            Post post9 = new Post("CI/CD Pipelines", List.of("ci", "cd", "devops"),
                "An overview of Continuous Integration and Continuous Deployment pipelines.");
            Post post10 = new Post("Security in Spring Boot", List.of("security", "spring", "authentication"),
                "Best practices for securing your Spring Boot applications.");

            postRepository.saveAll(List.of(post1, post2, post3, post4, post5, post6, post7, post8, post9, post10));
        }
    }
}
