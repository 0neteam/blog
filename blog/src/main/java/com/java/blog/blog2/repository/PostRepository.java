package com.java.blog.blog2.repository;

import com.java.blog.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "postRepository2")
public interface PostRepository extends JpaRepository<PostEntity, Integer> {
    List<PostEntity> findAll();
}
