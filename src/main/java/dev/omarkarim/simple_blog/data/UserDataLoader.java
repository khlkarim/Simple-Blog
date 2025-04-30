package dev.omarkarim.simple_blog.data;

import dev.omarkarim.simple_blog.model.User;
import dev.omarkarim.simple_blog.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("dev")
public class UserDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    public UserDataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            User user1 = new User("user1", "User 1", "mail1@gmail.com", "88999000");
            User user2 = new User("user2", "User 2", "mail2@gmail.com", "22555444");
            User user3 = new User("user3", "User 3", "mail3@gmail.com", "44111333");

            userRepository.saveAll(List.of(user1, user2, user3));
        }
    }
}