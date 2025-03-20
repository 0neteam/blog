package com.java.blog.blog.Repository;

import com.java.blog.blog.Entity.MenuEntity;
import com.java.blog.blog.Entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, Integer> {
    List<MenuEntity> findByUseYN(String useYN);
    //boardNo orderNo ref depth useYN
    List<MenuEntity> findByBoardNoAndOrderNoAndRefAndDepthAndUseYN(Integer boardNo, Integer orderNo, Integer ref, Integer depth, String useYN);


    public List<MenuEntity> findByBoardNoAndRefAndUseYN(Integer boardNo, Integer ref, String useYN);
    public List<MenuEntity> findByBoardNoAndUseYN(Integer boardNo, String useYN);

}
