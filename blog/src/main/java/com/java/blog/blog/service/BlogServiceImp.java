package com.java.blog.blog.service;

import com.java.blog.blog.dto.MenuDTO;
import com.java.blog.blog.dto.PostDTO;
import com.java.blog.blog.dto.PostResDTO;
import com.java.blog.blog.repository.BoardRepository;
import com.java.blog.blog.repository.MenuRepository;
import com.java.blog.blog.repository.UserRepository;
import com.java.blog.entity.BoardEntity;
import com.java.blog.entity.MenuEntity;
import com.java.blog.entity.PostEntity;
import com.java.blog.blog.repository.PostRepository;
import com.java.blog.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service(value = "blogServiceImp1")
public class BlogServiceImp implements BlogService {

    @Qualifier(value = "postRepository1")
    private final PostRepository postRepository;

    @Qualifier(value = "userRepository1")
    private final UserRepository userRepository;

    @Qualifier(value = "menuRepository1")
    private final MenuRepository menuRepository;

    @Qualifier(value = "boardRepository1") //메뉴 불러오는 목적으로 가져옴.
    private final BoardRepository boardRepository;

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
        getMenu(domain,model);
        model.addAttribute("domain",domain);
        model.addAttribute("post", postDTO);

    }

    //메뉴 불러오는 목적으로 가져옴.
    public void getMenu(String domain, Model model) {
        Optional<BoardEntity> boardSelect = boardRepository.findByTypeAndDomain(2, domain);
        if (boardSelect.isEmpty()) {
            throw new IllegalArgumentException("Board not found for domain: " + domain);
        }
        BoardEntity board = boardSelect.get();
        Integer boardNo = board.getNo();
        List<MenuEntity> menus = menuRepository.findByBoardNoAndRefAndUseYNOrderByOrderNoAsc(boardNo, 0, 'Y');
        List<MenuDTO> filterMenus = new ArrayList<>();

        for (MenuEntity menu : menus) {
            List<MenuEntity> filteredChildren = new ArrayList<>();
            for (MenuEntity child : menu.getChildren()) {
                if (child.getUseYN() == 'Y') {
                    filteredChildren.add(child);
                }
            }
            filterMenus.add(new MenuDTO(
                    menu.getNo(),
                    menu.getBoard().getNo(),
                    menu.getOrderNo(),
                    menu.getDepth(),
                    menu.getName(),
                    menu.getRef(),
                    menu.getUseYN(),
                    filteredChildren // 필터링된 children 리스트
            ));
        }
        model.addAttribute("domain",domain);
        model.addAttribute("menuList",filterMenus);
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

    @Override
    public PostResDTO writeDel(Integer no) {
        String status = "fail";
        try {
            PostEntity post = postRepository.findById(no).orElseThrow();
            post.setUseYN('N');
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
