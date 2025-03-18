package com.java.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/blogList")
    public String blogList(){
        return "blogList";
    }

    @GetMapping("/blogPost")
    public String blogPost(){
        return "blogPost";
    }

    @GetMapping("/postDetail")
    public String postDetail(){
        return "postDetail";
    }

    @GetMapping("/blogPostEdit")
    public String blogPostEdit() { return "blogPostEdit"; }
}
