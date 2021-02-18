package com.github.unei95.spring.demo.repositories;

import com.github.unei95.spring.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
