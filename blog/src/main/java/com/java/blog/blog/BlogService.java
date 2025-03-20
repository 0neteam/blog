package com.java.blog.blog;


import org.springframework.ui.Model;

public interface BlogService {
    String postDetail(Model model, Integer no);

    String deletePost(Integer no);

    String getMenu(Model model);
}
