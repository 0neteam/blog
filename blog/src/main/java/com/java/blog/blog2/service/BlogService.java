package com.java.blog.blog2.service;

import com.java.blog.blog2.dto.PostDTO;
import com.java.blog.entity.MenuEntity;
import com.java.blog.entity.PostEntity;
import com.java.blog.entity.BoardEntity;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface BlogService {
    public Map<String, Object> findList(HttpServletRequest req);
    public List<MenuEntity> findBoardNo(int boardNo);
    public int savePost(PostDTO postDTO, String domain, HttpServletRequest req);
    public String createBlog(HttpServletRequest req);
    public BoardEntity findBoardByDomain(String domain);
    public Map<BoardEntity, List<PostEntity>> getHomeData();
    public boolean hasBlog(int userNo);
    public Map<String, Object> findPostsByMenu(String domain, Integer menuNo);
}
