package com.java.blog.blog2.repository;

import com.java.blog.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository(value = "boardRepositor2")
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {

    Optional<BoardEntity> findByNo(int no);

}
