package com.java.blog.blog.service;

import com.java.blog.blog.dto.PostDTO;
import com.java.blog.entity.MenuEntity;
import com.java.blog.entity.PostEntity;
import com.java.blog.blog.repository.MenuRepository;
import com.java.blog.blog.repository.PostRepository;
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

//    @Override
//    public String postDetail(Model model, Integer no) {
//        PostEntity postEntity = postRepository.findById(no).orElseThrow();
//        model.addAttribute("post",postEntity);
//        model.addAttribute("boardData", postEntity.getContent());
//        getMenu(model); // 화면에 메뉴 표시용
//        return "blogPostDetail";
//    }


//    @Override
//    public char deletePost(Integer no) {
//        PostEntity postEntity = postRepository.findById(no).orElseThrow();
//        postEntity.setModDate(LocalDateTime.now());//삭제도 일종의 수정이니 삭제일 입력
//        postEntity.setUseYN('N');
//        postEntity = postRepository.save(postEntity);
//        //System.out.printf("postEntity의 값은: "+postEntity.getUseYN());
//        return postEntity.getUseYN(); // Y -> 삭제실패, N -> 삭제성공
//    }
//
//    //@Query
//    @Override
//    public String getMenu(Model model) {
//
//        //System.out.printf("getMenu 는 미완성 메서드임!!!!!!!!! 반드시 수정해야함!!!!");
//        /*
//        조건이 있음.
//        boardNo = 1 이어야함(일단 블로그는 단일인 상황이니까)
//        orderNo 로 메뉴순번 정렬
//        ref(매뉴ref) no 를 참조함.
//        depth(메뉴레벨)로 sub메뉴 정렬
//        useYN을 참고하여 Y만 나오게 할것.
//         */
//        List<MenuEntity> menuEntities = menuRepository.findByBoardNoAndUseYN(1, 'Y');
//        //System.out.printf("list of menuEntities: "+menuEntities+"\n");
//        model.addAttribute("menuList",menuEntities);
//        return "";
//    }

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
}
