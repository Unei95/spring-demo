package com.github.unei95.spring.demo.controllers;

import java.util.List;

import com.github.unei95.spring.demo.exceptions.UserNotFoundException;
import com.github.unei95.spring.demo.models.User;
import com.github.unei95.spring.demo.repositories.UserRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class UserController {

    private final UserRepository repository;

    UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/users")
    List<User> get() {
        return repository.findAll();
    }

    @PostMapping("/users")
    User post(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    @GetMapping("/users/{id}")
    User get(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping("/users/{id}")
    User put(@RequestBody User newUser, @PathVariable Long id) {

        return repository.findById(id)

                .map(user -> {
                    user.setFirstName(newUser.getFirstName());
                    user.setLastName(newUser.getLastName());
                    user.setEmail(newUser.getEmail());
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });
    }

    @DeleteMapping("/users/{id}")
    void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}