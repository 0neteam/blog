package com.java.blog.blog2.service;

import com.java.blog.entity.MenuEntity;
import com.java.blog.entity.PostEntity;
import com.java.blog.entity.BoardEntity;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface BlogService {
    Map<String, Object> findList(HttpServletRequest req);
    List<MenuEntity> findBoardNo(int boardNo);
    void savePost(PostEntity post, String domain);
    String createBlog(HttpServletRequest req);
    BoardEntity findBoardByDomain(String domain);
}
