package com.java.blog.blog2.controller;

import com.java.blog.blog2.service.BlogServiceImp;
import com.java.blog.entity.PostEntity;
import com.java.blog.entity.BoardEntity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller("blogController2")
@RequiredArgsConstructor
public class BlogController {

    @Qualifier("blogServiceImp2")
    private final BlogServiceImp blogService;

    @GetMapping("/blog/home")
    public String blogHome() {
        return "blogHome";
    }

    // 블로그 생성 요청: 생성 후 도메인 blog/list/default 이런 느낌
    @PostMapping("/blog/create")
    public ResponseEntity<String> createBlog(HttpServletRequest req) {
        String domain = blogService.createBlog(req);
        return ResponseEntity.ok(domain);
    }

    // 도메인에 해당하는 블로그 리스트 조회
    @GetMapping("/blog/list/{domain}")
    public String blogListByDomain(@PathVariable("domain") String domain, Model model, HttpServletRequest req) {
        req.setAttribute("domain", domain);
        model.addAllAttributes(blogService.findList(req));
        model.addAttribute("domain", domain);
        return "blogList";
    }

    // 글 작성 페이지: 도메인에 해당하는 BoardEntity를 조회한 후, 해당 board의 메뉴 목록을 전달
    @GetMapping("/blog/list/{domain}/post")
    public String blogPost(@PathVariable("domain") String domain, Model model, HttpServletRequest req) {
        BoardEntity board = blogService.findBoardByDomain(domain);
        model.addAttribute("menus", blogService.findBoardNo(board.getNo()));
        model.addAttribute("post", new PostEntity());
        model.addAttribute("domain", domain);
        return "blogPost";
    }

    // 글 저장: 도메인 정보를 포함하여 저장 후 해당 도메인의 리스트 페이지로 리다이렉트
    @PostMapping("/blog/list/{domain}/savePost")
    public String savePost(@PathVariable("domain") String domain, PostEntity post) {
        blogService.savePost(post, domain);
        return "redirect:/blog/list/" + domain;
    }
}
