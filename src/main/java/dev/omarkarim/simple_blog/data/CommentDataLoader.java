package dev.omarkarim.simple_blog.data;

import dev.omarkarim.simple_blog.model.Comment;
import dev.omarkarim.simple_blog.model.Post;
import dev.omarkarim.simple_blog.model.User;
import dev.omarkarim.simple_blog.repository.CommentRepository;
import dev.omarkarim.simple_blog.repository.PostRepository;
import dev.omarkarim.simple_blog.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@DependsOn("postDataLoader")
public class CommentDataLoader {
    private UserRepository userRepository;
    private PostRepository postRepository;
    private CommentRepository commentRepository;

    public CommentDataLoader(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @PostConstruct
    public void loadCommentData() {
        if (commentRepository.count() == 0) {
            List <User> users = userRepository.findAll();
            List <Post> posts = postRepository.findAll();

            List<Comment> comments = List.of(
                new Comment("This post clearly explains RESTful principles, great job!", posts.get(0), users.get(0)),
                new Comment("Loved your insights into API versioning strategies.", posts.get(1), users.get(0)),
                new Comment("OAuth 2.0 examples here are super helpful, thank you!", posts.get(2), users.get(0)),
                new Comment("I’d recommend explaining handling deprecated API endpoints next time.", posts.get(3), users.get(0)),
                new Comment("The section on error codes is very well-written and concise, great job!", posts.get(4), users.get(0)),
                new Comment("API rate-limiting is a tough topic, but you explained it well.", posts.get(5), users.get(0)),
                new Comment("Really enjoyed the section on API lifecycle management!", posts.get(6), users.get(0)),
                new Comment("Have you considered discussing WebSocket APIs in your next post?", posts.get(7), users.get(0)),
                new Comment("Loads of great information here about API gateways, thank you!", posts.get(8), users.get(0)),
                new Comment("Phenomenal explanation of integrating OpenAPI documentation.", posts.get(9), users.get(0)),
                new Comment("I like your comparison between GraphQL and REST APIs!", posts.get(10), users.get(0)),
                new Comment("This post on API caching is super valuable, thanks for sharing.", posts.get(11), users.get(0)),
                new Comment("Amazing explanation of semantics in HTTP methods, thank you!", posts.get(12), users.get(0)),
                new Comment("Kudos for exploring security headers in APIs, great job!", posts.get(13), users.get(0)),
                new Comment("I enjoyed reading about async APIs, thanks!", posts.get(14), users.get(0)),
                new Comment("Your discussion on rate limits and quotas is a must-read!", posts.get(15), users.get(0)),

                new Comment("The code snippets in this post are clear and so easy to follow!", posts.get(0), users.get(1)),
                new Comment("I appreciate your detailed explanation of coding best practices.", posts.get(1), users.get(1)),
                new Comment("Your guide on handling edge cases is very comprehensive, great post!", posts.get(2), users.get(1)),
                new Comment("Simple yet effective solutions for testing — great content!", posts.get(3), users.get(1)),
                new Comment("Can you create a follow-up on advanced coding design patterns?", posts.get(4), users.get(1)),
                new Comment("Thank you for including examples of SOLID principles in action!", posts.get(5), users.get(1)),
                new Comment("Appreciate the detailed walkthrough of refactoring strategies.", posts.get(6), users.get(1)),
                new Comment("The post on legacy-code maintenance is very relatable, thank you!", posts.get(7), users.get(1)),
                new Comment("Your tips for simplifying logic in nested functions are spot-on.", posts.get(8), users.get(1)),
                new Comment("Great explanations of different testing frameworks, very helpful!", posts.get(9), users.get(1)),
                new Comment("I hadn’t considered these debugging techniques before, thanks!", posts.get(10), users.get(1)),
                new Comment("Exploring various error-handling strategies was refreshing. Thanks!", posts.get(11), users.get(1)),
                new Comment("Improving runtime performance through your tips helped me a lot!", posts.get(12), users.get(1)),
                new Comment("Would love to see more examples of code under test in future posts.", posts.get(13), users.get(1)),
                new Comment("The design principles described here are perfect for clean code.", posts.get(14), users.get(1)),
                new Comment("Your post on unit testing vs. integration testing clarified so many doubts!", posts.get(15), users.get(1)),

                new Comment("Debugging best practices like yours deserve way more attention!", posts.get(0), users.get(2)),
                new Comment("The breakdown of debugging tools was very useful, thanks!", posts.get(1), users.get(2)),
                new Comment("This guide on setting breakpoints in complex systems is gold.", posts.get(2), users.get(2)),
                new Comment("Great tips! I’ve been struggling with async debugging too.", posts.get(3), users.get(2)),
                new Comment("Step-by-step HTTP log analysis guide simplified debugging for me.", posts.get(4), users.get(2)),
                new Comment("Thank you for explaining root cause analysis in such depth.", posts.get(5), users.get(2)),
                new Comment("Great article on making debugging easier with custom loggers!", posts.get(6), users.get(2)),
                new Comment("I appreciated the section on debugging distributed systems, thank you.", posts.get(7), users.get(2)),
                new Comment("These debugging shortcuts save so much time — brilliant work!", posts.get(8), users.get(2)),
                new Comment("I’ll definitely use your script for automated log parsing.", posts.get(9), users.get(2)),
                new Comment("Thanks for demonstrating how to capture bug reproductions for testing!", posts.get(10), users.get(2)),
                new Comment("The checklist for troubleshooting intermittent bugs is a lifesaver!", posts.get(11), users.get(2)),
                new Comment("Thank you for mentioning how to debug memory leaks effectively.", posts.get(12), users.get(2)),
                new Comment("Your comparison of debugging CLI vs GUI tools is very insightful.", posts.get(13), users.get(2)),
                new Comment("Thanks for covering multithreading-related debugging! Awesome post.", posts.get(14), users.get(2)),
                new Comment("The debugging case studies here were really interesting to read. Great job!", posts.get(15), users.get(2)),

                new Comment("This summary of accessibility best practices was awesome. Thanks!", posts.get(0), users.get(3)),
                new Comment("I love reading about improving navigation in UIs. Great post!", posts.get(1), users.get(3)),
                new Comment("This discussion on user personas helps a lot with UX improvements.", posts.get(2), users.get(3)),
                new Comment("The article highlights great tools for usability testing. Loved it!", posts.get(3), users.get(3)),
                new Comment("Great points! Accessibility should always be an early focus in design.", posts.get(4), users.get(3)),
                new Comment("Amazing explanation of human-centered design principles here!", posts.get(5), users.get(3)),
                new Comment("Thank you for including examples of UX improvements post-testing.", posts.get(6), users.get(3)),
                new Comment("Your take on designing with user empathy is just phenomenal.", posts.get(7), users.get(3)),
                new Comment("Tips for prototyping with Figma were super helpful, thank you!", posts.get(8), users.get(3)),
                new Comment("The prioritization of simplicity in this discussion stood out to me!", posts.get(9), users.get(3)),
                new Comment("I appreciated your suggestions for designing error-friendly interfaces.", posts.get(10), users.get(3)),
                new Comment("Providing examples of real-world accessibility violations was insightful.", posts.get(11), users.get(3)),
                new Comment("Your content around color-blind friendly UI is very practical, thanks!", posts.get(12), users.get(3)),
                new Comment("Great suggestions for incorporating inclusive design early in processes!", posts.get(13), users.get(3)),
                new Comment("Your section on usability vs desirability is eye-opening. Thanks!", posts.get(14), users.get(3)),
                new Comment("I loved the UX industry trends shared in this post. Keep up the great work!", posts.get(15), users.get(3))
            );


            commentRepository.saveAll(comments);
        }
    }
}