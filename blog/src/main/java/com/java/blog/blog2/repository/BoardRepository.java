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
    // 동일 도메인 중 첫 번째 결과만 반환 (중복 생성이 의도되지 않았다면)
    Optional<BoardEntity> findFirstByDomain(String domain);

    // 블로그 타입(2)이고 사용중인 보드를 조회 (예: 네이버 블로그 홈에 보여줄 블로그들)
    List<BoardEntity> findByTypeAndUseYN(Integer type, char useYN);
}