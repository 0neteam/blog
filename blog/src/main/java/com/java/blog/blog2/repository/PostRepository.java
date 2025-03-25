package com.java.blog.blog2.repository;

import com.java.blog.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("postRepository2")
public interface PostRepository extends JpaRepository<PostEntity, Integer> {

    // 기존 메서드: List<PostEntity> findByMenu_Board_No(Integer boardNo);
    // 대신 아래와 같이 명시적인 JPQL 쿼리를 사용합니다.
    @Query("SELECT p FROM PostEntity p WHERE p.menu.board.no = :boardNo ORDER BY p.regDate DESC")
    List<PostEntity> findByBoardNoOrderByRegDateDesc(@Param("boardNo") Integer boardNo);

    // BoardEntity PK를 기준으로 최신 3개 글 조회
    List<PostEntity> findTop3ByMenu_Board_NoOrderByRegDateDesc(Integer boardNo);

}