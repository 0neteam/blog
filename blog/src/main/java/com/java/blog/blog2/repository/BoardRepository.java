package com.java.blog.blog2.repository;

import com.java.blog.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("boardRepository2")
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
    Optional<BoardEntity> findByNo(int no);
    Optional<BoardEntity> findByDomain(String domain);
    Optional<BoardEntity> findFirstByDomain(String domain);

    List<BoardEntity> findByTypeAndUseYN(Integer type, char useYN);

    boolean existsByRegUserNoAndType(Integer regUserNo, Integer type);

    boolean existsByDomainAndType(String domain, Integer type);

    Optional<BoardEntity> findFirstByRegUserNoAndType(Integer regUserNo, Integer type);
}