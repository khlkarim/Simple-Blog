package dev.omarkarim.simple_blog.data;

import dev.omarkarim.simple_blog.model.User;
import dev.omarkarim.simple_blog.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDataLoader {
    private UserRepository userRepository;

    public UserDataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void loadUserData() {
        if (userRepository.count() == 0) {
            List<User> users = List.of(
                new User("apibuilder", "Maxwell Riggs", "maxwell.r@exampleapi.com", "5558675309"),
                new User("coderfanatic", "Elena Vega", "elena.vega@codehub.dev", "5553941287"),
                new User("debugwizard", "Trevor Lane", "trevor.lane@debugzone.com", "5552384910"),
                new User("uxexplorer", "Harper Nguyen", "harper.nguyen@uxdesign.pro", "5551742836")
            );
            userRepository.saveAll(users);
        }
    }
}
