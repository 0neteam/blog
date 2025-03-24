package com.java.blog.blog.repository;

import com.java.blog.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "boardRepository1")
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
    BoardEntity findByNo(int no);

}
