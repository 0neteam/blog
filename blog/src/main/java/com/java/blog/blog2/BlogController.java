package com.java.blog.blog2;

import com.java.blog.blog2.Entity.PostEntity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class BlogController {

    private final BlogServiceImp blogService;

    @GetMapping("/blog/list")
    public String blogList(Model model, HttpServletRequest req) {
        model.addAllAttributes(blogService.findList(req));
        return "blogList";
    }

    @GetMapping("/blog/post")
    public String blogPost(Model model, HttpServletRequest req) {
        model.addAttribute("menus", blogService.findBoardNo(1));
        model.addAttribute("post", new PostEntity());
        return "blogPost";
    }

    @PostMapping("/blog/savePost")
    public String savePost(@ModelAttribute PostEntity post, Model model) {
        blogService.savePost(post);
        return "redirect:/blog/list";
    }

    @GetMapping("/postDetail")
    public String postDetail(){
        return "postDetail";
    }

    @GetMapping("/blogPostEdit")
    public String blogPostEdit() { return "blogPostEdit"; }
}
