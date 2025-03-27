package com.java.blog.blog2.controller;

import com.java.blog.blog2.dto.PostDTO;
import com.java.blog.blog2.service.BlogService;
import com.java.blog.config.Utils;
import com.java.blog.entity.BoardEntity;
import com.java.blog.entity.MenuEntity;
import com.java.blog.entity.PostEntity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller("blogPostController")
@RequiredArgsConstructor
@RequestMapping("/{domain}/post")
public class BlogPostController {

    private final BlogService blogService;
    private final Utils utils;

    @GetMapping
    public String blogPost(@PathVariable String domain, Model model, HttpServletRequest req) {
        String userNoStr = utils.getUserNo(req);
        if (userNoStr == null) return "redirect:/login";
        int userNo = Integer.parseInt(userNoStr);

        BoardEntity board = blogService.findBoardByDomain(domain);
        if (board.getRegUserNo() != userNo) return "error/403";

        model.addAttribute("post", new PostDTO());
        model.addAttribute("menus", blogService.findBoardNo(board.getNo()));
        model.addAttribute("domain", domain);
        model.addAttribute("showAside", false);
        return "blogPost";
    }

    @PostMapping("/save")
    @ResponseBody
    public Map<String, Object> savePost(
            @PathVariable String domain,
            @RequestBody PostDTO postDTO,
            HttpServletRequest req) {

        String userNoStr = utils.getUserNo(req);
        if (userNoStr == null) throw new RuntimeException("로그인 필요");
        int userNo = Integer.parseInt(userNoStr);

        BoardEntity board = blogService.findBoardByDomain(domain);
        if (board.getRegUserNo() != userNo) throw new RuntimeException("자신의 블로그만 작성 가능");

        int savedNo = blogService.savePost(postDTO, domain, req);
        return Map.of("status", "success", "no", savedNo);
    }
}
