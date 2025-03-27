package com.java.blog.blog.service;


import com.java.blog.blog.dto.PostDTO;
import com.java.blog.blog.dto.PostResDTO;
import com.java.blog.entity.BoardEntity;
import com.java.blog.entity.MenuEntity;
import com.java.blog.entity.PostEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

public interface BlogService {
    void read(String domain, Integer no, Model model);

    PostResDTO writeEdit(Integer no, PostDTO postDTO);

    PostResDTO writeDel(Integer no);

    public List<MenuEntity> findBoardNo(int boardNo);
    public void savePost(com.java.blog.blog2.dto.PostDTO postDTO, String domain, HttpServletRequest req);
    public BoardEntity findBoardByDomain(String domain);
}
