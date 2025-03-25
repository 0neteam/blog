package com.java.blog.blog2.service;

import com.java.blog.entity.MenuEntity;
import com.java.blog.entity.PostEntity;
import com.java.blog.entity.BoardEntity;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface BlogService {
    public Map<String, Object> findList(HttpServletRequest req);
    public List<MenuEntity> findBoardNo(int boardNo);
    public void savePost(PostEntity post, String domain);
    public String createBlog(HttpServletRequest req);
    public BoardEntity findBoardByDomain(String domain);
    public Map<BoardEntity, List<PostEntity>> getHomeData();
    public boolean hasBlog(int userNo);
}
