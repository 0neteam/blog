package com.java.blog.blog.service;

import com.java.blog.blog.dto.MenuDTO;
import com.java.blog.blog.dto.PostDTO;
import com.java.blog.blog.dto.PostResDTO;
import com.java.blog.blog.repository.BoardRepository;
import com.java.blog.blog.repository.MenuRepository;
import com.java.blog.blog.repository.UserRepository;
import com.java.blog.config.Utils;
import com.java.blog.entity.BoardEntity;
import com.java.blog.entity.MenuEntity;
import com.java.blog.entity.PostEntity;
import com.java.blog.blog.repository.PostRepository;
import com.java.blog.entity.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
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

    private final Utils utils;

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

    @Override
    public List<MenuEntity> findBoardNo(int boardNo) {
        return menuRepository.findByBoard_No(boardNo);
    }



    @Override
    public void savePost(com.java.blog.blog2.dto.PostDTO postDTO, String domain, HttpServletRequest req) {
        // JWT 토큰에서 실제 로그인된 사용자 번호 추출
        String userNoStr = utils.getUserNo(req);
        if (userNoStr == null || userNoStr.isEmpty()) {
            throw new RuntimeException("로그인이 필요합니다.");
        }
        int userNo = Integer.parseInt(userNoStr);

        // PostDTO를 PostEntity로 변환 (예시: 직접 변환)
        PostEntity post = new PostEntity();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        // 여기서 실제 로그인된 사용자의 번호를 할당합니다.
        post.setRegUserNo(userNo);

        // 메뉴 번호를 기반으로 MenuEntity 조회
        MenuEntity menu = menuRepository.findById(postDTO.getMenuNo())
                .orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다."));
        post.setMenu(menu);

        // 도메인에 맞는 BoardEntity 조회
        BoardEntity board = findBoardByDomain(domain);
        post.setRegDate(LocalDateTime.now());
        post.setViewCount(0);
        post.setUseYN('Y');

        postRepository.save(post);
    }

    @Override
    public BoardEntity findBoardByDomain(String domain) {
        return boardRepository.findFirstByDomain(domain)
                .orElseThrow(() -> new RuntimeException("해당 도메인의 게시판이 없습니다."));
    }
}
