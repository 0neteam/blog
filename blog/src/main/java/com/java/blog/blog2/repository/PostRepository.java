package com.java.blog.blog2.repository;

import com.java.blog.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("postRepository2")
public interface PostRepository extends JpaRepository<PostEntity, Integer> {

    // BoardEntity의 no를 기준으로 글 목록을 최신순으로 조회하는 JPQL 쿼리
    @Query("SELECT p FROM PostEntity p WHERE p.menu.board.no = :boardNo ORDER BY p.regDate DESC")
    List<PostEntity> findByBoardNoOrderByRegDateDesc(@Param("boardNo") Integer boardNo);

    // BoardEntity의 no를 기준으로 최신 3개 글 조회
    List<PostEntity> findTop3ByMenu_Board_NoOrderByRegDateDesc(Integer boardNo);
}
