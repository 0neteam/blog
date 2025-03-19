package com.java.blog.blog.Repository;

import com.java.blog.blog.Entity.MenuEntity;
import com.java.blog.blog.Entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, Integer> {
    List<MenuEntity> findByUseYN(String useYN);
}
