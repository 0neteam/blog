package com.java.blog.blog.controller;

import com.java.blog.blog.dto.PostDTO;
import com.java.blog.blog.dto.PostResDTO;
import com.java.blog.blog.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


//blog2의 BlogPostController 의 reqMapping과 겹치기때문에 임시로 post2로 변경.
@RequestMapping("/{domain}/post2")
@RequiredArgsConstructor
@Controller(value = "blogController1")
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
    //


    @Qualifier(value = "blogServiceImp1")
    private final BlogService blogService;

//    @GetMapping
//    public String postDetail(Model model, @RequestParam(name = "no") Integer no){
//        return blogService.postDetail(model, no);
//    }

    @GetMapping("/{no:[0-9]+}")
    public String read(@PathVariable("domain") String domain, @PathVariable("no") Integer no, Model model) {
        blogService.read(domain, no, model);
        return "blogPostDetail";
    }

    // 포스트 수정
    @PatchMapping("/{no:[0-9]+}")
    @ResponseBody
    public PostResDTO writeEdit(@PathVariable("no") Integer no, @RequestBody PostDTO postDTO) {
        return blogService.writeEdit(no, postDTO);
    }

}
