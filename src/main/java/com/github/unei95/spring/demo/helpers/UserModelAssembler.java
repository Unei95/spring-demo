package com.github.unei95.spring.demo.helpers;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.github.unei95.spring.demo.controllers.UserController;
import com.github.unei95.spring.demo.entities.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {

    @Override
    public EntityModel<User> toModel(User employee) {

        return EntityModel.of(employee,
                linkTo(methodOn(UserController.class).get(employee.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).get((String) null)).withRel("users").expand());
    }
}
