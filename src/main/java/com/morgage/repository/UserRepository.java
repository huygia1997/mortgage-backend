package com.morgage.repository;

import com.morgage.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Integer> {
User findByUsername(String name);
User findByToken(String token);
}
