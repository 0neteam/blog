package com.java.blog.blog2.controller;

import com.java.blog.blog2.service.BlogService;
import com.java.blog.config.Utils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller("blogHomeController")
@RequiredArgsConstructor
@RequestMapping("/")
public class BlogHomeController {

    private final BlogService blogService;
    private final Utils utils;

    // 사이트 전체 블로그 홈 페이지
    @GetMapping
    public String blogHome(Model model, HttpServletRequest req) {
        String userNoStr = utils.getUserNo(req);
        int userNo = 0;
        if (userNoStr != null && !userNoStr.isBlank()) {
            try {
                userNo = Integer.parseInt(userNoStr);
            } catch (NumberFormatException ignored) {}
        }
        model.addAttribute("showAside", false);
        model.addAttribute("hasBlog", blogService.hasBlog(userNo));
        model.addAttribute("homeData", blogService.getHomeData());
        return "blogHome"; // 뷰: blogHome.html
    }

    // 블로그 생성 요청 (생성 후 생성된 도메인을 반환)
    @PostMapping("blog/create")
    public ResponseEntity<String> createBlog(HttpServletRequest req) {
        String domain = blogService.createBlog(req);
        return ResponseEntity.ok(domain);
    }

}
