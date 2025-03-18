package com.java.blog.blog.Repository;

import com.java.blog.blog.Entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, Integer> {
}
