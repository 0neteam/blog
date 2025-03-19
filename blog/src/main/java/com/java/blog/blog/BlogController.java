package com.java.blog.blog;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class BlogController {
//    @GetMapping("/blogList")
//    public String blogList(){
//        return "blogList";
//    }
//
//    @GetMapping("/blogPost")
//    public String blogPost(){
//        return "blogPost";
//    }

    private final BlogService blogService;

    @GetMapping("/postDetail")
    public String postDetail(Model model, @RequestParam(name = "no") Integer no){
        return blogService.postDetail(model, no);
    }

    @GetMapping("/blogPostEdit")
    public String blogPostEdit() { return "blogPostEdit"; }

    @ResponseBody
    @PostMapping("deletePost")
    public String deletePost(@RequestParam("no") Integer no){
        return blogService.deletePost(no);
    }

//    @ResponseBody
//    @PostMapping("getMenu")
//    public String getMenu(Model model){
//        return blogService.getMenu(model);
//    }

//    @ResponseBody
//    @PostMapping("deleteMenu")
//    public String deleteMenu(@RequestParam("no") Integer no){
//        return blogService.deleteMenu(no);
//    }
}
