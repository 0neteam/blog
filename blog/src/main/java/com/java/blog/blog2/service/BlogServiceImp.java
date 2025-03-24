package com.java.blog.blog2.service;

import com.java.blog.entity.BoardEntity;
import com.java.blog.entity.MenuEntity;
import com.java.blog.entity.PostEntity;
import com.java.blog.blog2.repository.BoardRepository;
import com.java.blog.blog2.repository.MenuRepository;
import com.java.blog.blog2.repository.PostRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "blogServiceImp2")
@RequiredArgsConstructor
public class BlogServiceImp implements BlogService {

    @Qualifier(value = "postRepository2")
    private final PostRepository postRepository;
    @Qualifier(value = "menuRepository2")
    private final MenuRepository menuRepository;
    @Qualifier(value = "boardRepository2")
    private final BoardRepository boardRepository;

    public Map<String, Object> findList(HttpServletRequest req) {
        List<PostEntity> posts = postRepository.findAll();
        Map<String, Object> result = new HashMap<>();
        result.put("posts", posts);
        return result;
    }

    public List<MenuEntity> findBoardNo(int boardNo) {
        return menuRepository.findByBoardNo(boardNo);
    }

    // 글 저장 기능 (boardNo = 1 하드코딩)
    public void savePost(PostEntity post) {
        if (post.getRegUserNo() == null) {
            post.setRegUserNo(1); // 기본값 설정
        }

        MenuEntity menu = menuRepository.findById(post.getMenu().getNo())
                .orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다."));

        BoardEntity board = boardRepository.findByNo(1)
                .orElseThrow(() -> new RuntimeException("게시판을 찾을 수 없습니다."));

        post.setMenu(menu);
        post.setRegDate(LocalDateTime.now());
        post.setViewCount(0);
        post.setUseYN("Y");

        postRepository.save(post);
    }

}
