package com.github.unei95.spring.demo;

import com.github.unei95.spring.demo.models.User;
import com.github.unei95.spring.demo.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(UserRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new User("first", "user", "first@user")));
            log.info("Preloading " + repository.save(new User("second", "user", "second@user")));
        };
    }
}
