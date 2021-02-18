package com.github.unei95.spring.demo.helpers;

import com.github.unei95.spring.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
