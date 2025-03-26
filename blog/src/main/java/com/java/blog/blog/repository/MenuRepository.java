package com.java.blog.blog.repository;

import com.java.blog.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "menuRepository1")
public interface MenuRepository extends JpaRepository<MenuEntity, Integer> {
    MenuEntity findByNo(int no);
    public List<MenuEntity> findByBoardNoAndUseYN(Integer boardNo, char useYN);

    public List<MenuEntity> findByBoardNoAndRefAndUseYNOrderByOrderNoAsc(Integer boardNo, Integer ref, char useYN);

}
