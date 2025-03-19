package com.java.blog.blog;

import com.java.blog.blog.Entity.MenuEntity;
import com.java.blog.blog.Entity.PostEntity;
import com.java.blog.blog.Repository.MenuRepository;
import com.java.blog.blog.Repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogServiceImp implements BlogService {
    private final PostRepository postRepository;
    private final MenuRepository menuRepository;

    @Override
    public String postDetail(Model model, Integer no) {
        PostEntity postEntity = postRepository.findById(no).orElseThrow();
        model.addAttribute("post",postEntity);
        return "postDetail";
    }

    @Modifying
    @Override
    public String deletePost(Integer no) {
        PostEntity postEntity = postRepository.findById(no).orElseThrow();
        postEntity.setModDate(LocalDateTime.now());//삭제도 일종의 수정이니 삭제일 입력
        postEntity.setUseYN("N");
        postEntity = postRepository.save(postEntity);
        //System.out.printf("postEntity의 값은: "+postEntity.getUseYN());
        return postEntity.getUseYN(); // Y -> 삭제실패, N -> 삭제성공
    }

    //@Query
    @Override
    public String getMenu(Model model) {
        List<MenuEntity> menuEntities = menuRepository.findByUseYN("Y");
        model.addAttribute("menuList",menuEntities);
        return "";
    }
}
