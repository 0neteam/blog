package com.java.blog.blog.repository;

import com.java.blog.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "postRepository1")
public interface PostRepository extends JpaRepository<PostEntity, Integer> {

}
