package com.java.blog.blog2.repository;

import com.java.blog.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "menuRepository2")
public interface MenuRepository extends JpaRepository<MenuEntity, Integer> {

    List<MenuEntity> findByBoardNo(int boardNo);

}
