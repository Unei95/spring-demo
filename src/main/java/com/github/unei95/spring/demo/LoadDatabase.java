package com.github.unei95.spring.demo;

import com.github.unei95.spring.demo.entities.User;
import com.github.unei95.spring.demo.helpers.UserRepository;
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
            log.info("Preloading " + repository.save(new User("Klaus", "Sievers", "klaus@sievers.de")));
            log.info("Preloading " + repository.save(new User("Klaus", "Kappenstiel", "klaus@kappenstiel.de")));
            log.info("Preloading " + repository.save(new User("Elke", "Kappenstiel-Sievers", "elke@kappenstiel-sievers.de")));
        };
    }
}
