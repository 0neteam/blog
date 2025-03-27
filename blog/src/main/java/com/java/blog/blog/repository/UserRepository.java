package com.java.blog.blog.repository;

import com.java.blog.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "userRepository1")
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}

