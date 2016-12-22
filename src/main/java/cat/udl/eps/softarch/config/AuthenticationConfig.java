package cat.udl.eps.softarch.config;

import cat.udl.eps.softarch.domain.User;
import cat.udl.eps.softarch.repository.UserRepository;
import cat.udl.eps.softarch.service.BasicUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Configuration
public class AuthenticationConfig extends GlobalAuthenticationConfigurerAdapter {

    @Autowired Environment environment;
    @Autowired BasicUserDetailsService userDetailsService;
    @Autowired UserRepository userRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService)
            .passwordEncoder(new BCryptPasswordEncoder());
        auth.inMemoryAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .withUser("admin")
                    .password("$2a$10$B1dcscvS/lgiBnGdkhhupew8AhbjqUL7TjdA2ggvxQhs5jN7KVSMC")
                    .roles("ADMIN");

        //Default users
        if (!userRepository.exists("user1")) {
            User user1 = new User();
            user1.setUsername("user1");
            user1.setPassword("$2a$10$B1dcscvS/lgiBnGdkhhupew8AhbjqUL7TjdA2ggvxQhs5jN7KVSMC");
            user1.setName("User 1");
            userRepository.save(user1);
        }
        if (!userRepository.exists("user2")) {
            User user2 = new User();
            user2.setUsername("user2");
            user2.setPassword("$2a$10$B1dcscvS/lgiBnGdkhhupew8AhbjqUL7TjdA2ggvxQhs5jN7KVSMC");
            user2.setName("User 2");
            userRepository.save(user2);
        }
        if (!userRepository.exists("system")) {
            User system = new User();
            system.setUsername("system");
            system.setPassword("$2a$10$B1dcscvS/lgiBnGdkhhupew8AhbjqUL7TjdA2ggvxQhs5jN7KVSMC");
            system.setName("System");
            userRepository.save(system);
        }

        //Testing users, when not deployed in Heroku
        if(!environment.acceptsProfiles("heroku") &&
                !userRepository.exists("user")) {
            User user = new User();
            user.setUsername("user");
            user.setPassword(new BCryptPasswordEncoder().encode("password"));
            user.setName("User 1");
            userRepository.save(user);
        }
    }
}