package com.github.unei95.spring.demo.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.github.unei95.spring.demo.exceptions.UserNotFoundException;
import com.github.unei95.spring.demo.entities.User;
import com.github.unei95.spring.demo.helpers.UserModelAssembler;
import com.github.unei95.spring.demo.helpers.UserRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public
class UserController {

    private final UserRepository repository;
    private final UserModelAssembler assembler;

    UserController(UserRepository repository, UserModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/users")
    public CollectionModel<EntityModel<User>> get(@RequestParam(required = false) String firstName) {
        List<EntityModel<User>> users;
        if (firstName != null)
        {
            User user = new User();
            user.setFirstName(firstName);

            ExampleMatcher matcher = ExampleMatcher
                    .matchingAny()
                    .withMatcher("firstName", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withIgnorePaths("lastName", "email");

            Example<User> example = Example.of(user, matcher);
            users = repository.findAll(example).stream()
                    .map(assembler::toModel)
                    .collect(Collectors.toList());
        }
        else {
            users = repository.findAll().stream()
                    .map(assembler::toModel)
                    .collect(Collectors.toList());
        }


        return CollectionModel.of(users, linkTo(methodOn(UserController.class).get(firstName)).withSelfRel().expand());
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> get(@PathVariable Long id) {

        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return assembler.toModel(user);
    }

    @PostMapping("/users")
    public ResponseEntity<?> post(@RequestBody User newUser) {
        EntityModel<User> entityModel = assembler.toModel(repository.save(newUser));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> put(@RequestBody User newUser, @PathVariable Long id) {
        User updatedUser = repository.findById(id)
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

        EntityModel<User> entityModel = assembler.toModel(updatedUser);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}