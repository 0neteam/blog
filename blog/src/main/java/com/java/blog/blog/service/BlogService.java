package com.java.blog.blog.service;


import com.java.blog.blog.dto.PostDTO;
import com.java.blog.blog.dto.PostResDTO;
import org.springframework.ui.Model;

public interface BlogService {
    void read(String domain, Integer no, Model model);

    PostResDTO writeEdit(Integer no, PostDTO postDTO);
}
