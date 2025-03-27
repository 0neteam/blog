package com.java.blog.blog2.controller;

import com.java.blog.blog2.dto.PostDTO;
import com.java.blog.blog2.service.BlogService;
import com.java.blog.entity.BoardEntity;
import com.java.blog.entity.MenuEntity;
import com.java.blog.entity.PostEntity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller("blogPostController")
@RequiredArgsConstructor
@RequestMapping("/{domain}/post")
public class BlogPostController {

    private final BlogService blogService;

    // 글 작성 페이지 (GET /{domain}/post)
    @GetMapping
    public String blogPost(@PathVariable("domain") String domain, Model model, HttpServletRequest req) {
        // 도메인에 맞는 BoardEntity 조회
        BoardEntity board = blogService.findBoardByDomain(domain);
        // Board에 속한 메뉴 목록 조회
        List<MenuEntity> menus = blogService.findBoardNo(board.getNo());
        // PostEntity를 생성할 때 menu 객체 초기화 (nested binding을 위해)
        PostEntity post = new PostEntity();
        post.setMenu(new MenuEntity());

        model.addAttribute("post", post);
        model.addAttribute("menus", menus);
        model.addAttribute("domain", domain);
        return "blogPost"; // 뷰 템플릿 blogPost.html
    }

    // 글 저장 요청 (POST /{domain}/post/save)
    @PostMapping("/save")
    public String savePost(@PathVariable("domain") String domain, PostDTO postDTO, HttpServletRequest req) {
        blogService.savePost(postDTO, domain, req);
        return "redirect:/" + domain;
    }
}
