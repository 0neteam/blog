package com.java.blog.blog2;

import com.java.blog.blog2.Entity.MenuEntity;
import com.java.blog.blog2.Entity.PostEntity;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

public interface BlogService {

    public Map<String, Object> findList(HttpServletRequest req);
    public List<MenuEntity> findBoardNo(int boardNo);
    public void savePost(PostEntity post);
}
