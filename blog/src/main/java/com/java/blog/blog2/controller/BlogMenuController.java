package com.java.blog.blog2.controller;

import com.java.blog.blog.dto.MenuDTO;
import com.java.blog.blog.repository.BoardRepository;
import com.java.blog.blog.repository.MenuRepository;
import com.java.blog.blog2.repository.PostRepository;
import com.java.blog.blog2.service.BlogService;
import com.java.blog.entity.BoardEntity;
import com.java.blog.entity.MenuEntity;
import com.java.blog.entity.PostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/{domain}")
public class BlogMenuController {

    private final BlogService blogService;

    @GetMapping("/{menuNo:[0-9]+}")
    public String showMenu(
            @PathVariable String domain,
            @PathVariable Integer menuNo,
            Model model) {
        Map<String, Object> data = blogService.findPostsByMenu(domain, menuNo);
        model.addAllAttributes(data);
        return "blogList";
    }


}