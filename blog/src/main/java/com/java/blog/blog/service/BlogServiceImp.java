package com.java.blog.blog.service;

import com.java.blog.blog.dto.PostDTO;
import com.java.blog.blog.dto.PostResDTO;
import com.java.blog.blog.repository.UserRepository;
import com.java.blog.entity.MenuEntity;
import com.java.blog.entity.PostEntity;
import com.java.blog.blog.repository.MenuRepository;
import com.java.blog.blog.repository.PostRepository;
import com.java.blog.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service(value = "blogServiceImp1")
public class BlogServiceImp implements BlogService {

    @Qualifier(value = "postRepository1")
    private final PostRepository postRepository;
    @Qualifier(value = "menuRepository1")
    private final MenuRepository menuRepository;
    @Qualifier(value = "userRepository1")
    private final UserRepository userRepository;

    @Override
    public void read(String domain, Integer no, Model model) {
        PostEntity post = postRepository.findById(no).orElseThrow();
        PostDTO postDTO = PostDTO.builder().build();
        if(post != null) {
            postDTO = PostDTO.builder()
                    .no(post.getNo())
                    .menuNo(post.getMenu().getNo())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .regName(post.getUser().getName())
                    .menuName(post.getMenu().getName())
                    .regDate(post.getRegDate())
                    .viewCount(post.getViewCount())
                    .build();
        }
        model.addAttribute("post", postDTO);
    }

    @Override
    public PostResDTO writeEdit(Integer no, PostDTO postDTO) {
        postDTO.setNo(no);
        String status = "fail";
        try{
            MenuEntity menu = menuRepository.findById(postDTO.getMenuNo()).orElseThrow();
            UserEntity user = userRepository.findById(1).orElseThrow();
            PostEntity post = postRepository.findById(no).orElseThrow();
            post.setTitle(postDTO.getTitle());
            post.setContent(postDTO.getContent());
            post.setModDate(LocalDateTime.now());
            post.setModUserNo(1);
            post = postRepository.save(post);
            if(post.getNo() > 0) {
                status = "success";
            }
        } catch (Exception e) {}
        return PostResDTO.builder()
                .status(status)
                .no(no)
                .build();
    }
}
