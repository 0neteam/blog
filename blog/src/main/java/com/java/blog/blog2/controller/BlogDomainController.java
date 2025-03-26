package com.java.blog.blog2.controller;

import com.java.blog.blog2.service.BlogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller("blogDomainController")
@RequiredArgsConstructor
@RequestMapping("/{domain}")
public class BlogDomainController {

    private final BlogService blogService;

    // 개별 블로그 홈 페이지
    @GetMapping
    public String blogHome(@PathVariable("domain") String domain, Model model, HttpServletRequest req) {
        req.setAttribute("domain", domain);
        model.addAllAttributes(blogService.findList(req));
        model.addAttribute("domain", domain);
        return "blogList"; // 뷰: blogList.html (블로그 목록 화면)
    }
}
