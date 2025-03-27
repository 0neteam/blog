package com.java.blog.blog2.controller;

import com.java.blog.blog.dto.PostResDTO;
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



//    // 글 작성 페이지 (GET /{domain}/post)
//    @GetMapping
//    public String blogPost(@PathVariable("domain") String domain, Model model, HttpServletRequest req) {
//        BoardEntity board = blogService.findBoardByDomain(domain);
//        List<MenuEntity> menus = blogService.findBoardNo(board.getNo());
//        // PostDTO로 폼 렌더링
//        PostDTO post = new PostDTO();
//        model.addAttribute("showAside", false);
//        model.addAttribute("post", post);
//        model.addAttribute("menus", menus);
//        model.addAttribute("domain", domain);
//        return "blogPost";
//    }
//
//    // 글 저장 요청 (POST /{domain}/post/save)
//    @PostMapping("/save")
//    public String savePost(@PathVariable("domain") String domain, PostDTO postDTO, HttpServletRequest req) {
//        blogService.savePost(postDTO, domain, req);
//        return "redirect:/" + domain;
//    }

}
