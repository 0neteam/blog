package com.java.blog.blog2.service;

import com.java.blog.blog2.repository.UserRepository;
import com.java.blog.config.Utils;
import com.java.blog.entity.BoardEntity;
import com.java.blog.entity.MenuEntity;
import com.java.blog.entity.PostEntity;
import com.java.blog.blog2.repository.BoardRepository;
import com.java.blog.blog2.repository.MenuRepository;
import com.java.blog.blog2.repository.PostRepository;
import com.java.blog.entity.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service("blogServiceImp2")
@RequiredArgsConstructor
public class BlogServiceImp implements BlogService {

    @Qualifier("postRepository2")
    private final PostRepository postRepository;
    @Qualifier("menuRepository2")
    private final MenuRepository menuRepository;
    @Qualifier("boardRepository2")
    private final BoardRepository boardRepository;
    private final Utils utils;

    private final UserRepository userRepository;

    // 게시글 목록 조회: request attribute "domain"를 통해 해당 BoardEntity를 조회하고, 그 board에 속하는 글들을 가져옴
    @Override
    public Map<String, Object> findList(HttpServletRequest req) {
        String domain = (String) req.getAttribute("domain");
        if (domain == null || domain.isEmpty()) {
            domain = "default";
        }
        // 기존 "blog/list/" 접두어를 제거
        // String fullDomain = "blog/list/" + domain;
        BoardEntity board = boardRepository.findFirstByDomain(domain)
                .orElseThrow(() -> new RuntimeException("해당 도메인의 게시판이 없습니다."));

        List<PostEntity> posts = postRepository.findByBoardNoOrderByRegDateDesc(board.getNo());

        // 디버깅용 로그 출력
        System.out.println("조회된 board.no: " + board.getNo());
        System.out.println("조회된 posts 수: " + posts.size());

        Map<String, Object> result = new HashMap<>();
        result.put("posts", posts);
        return result;
    }



    // 메뉴 목록 조회: boardNo를 이용하여 해당 게시판에 속한 메뉴들을 가져옴
    @Override
    public List<MenuEntity> findBoardNo(int boardNo) {
        return menuRepository.findByBoard_No(boardNo);
    }

    // 게시글 저장: 도메인 정보를 사용해 현재 BoardEntity를 조회하고 글을 저장
    @Override
    public void savePost(PostEntity post, String domain) {
        if (post.getRegUserNo() == null) {
            post.setRegUserNo(1);
        }
        MenuEntity menu = menuRepository.findById(post.getMenu().getNo())
                .orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다."));
        // ✂️ prefix 제거 — 이제 findBoardByDomain(domain) 호출만으로 충분
        BoardEntity board = findBoardByDomain(domain);

        post.setRegDate(LocalDateTime.now());
        post.setViewCount(0);
        post.setUseYN('Y');
        post.setMenu(menu);
        postRepository.save(post);
    }

    // 블로그 생성: 요청 파라미터(userName, userId)를 통해 BoardEntity 생성 및 기본 메뉴 생성
    @Override
    @Transactional
    public String createBlog(HttpServletRequest req) {
        // JWT에서 userNo 추출
        String userNoStr = utils.getUserNo(req);
        if(userNoStr == null) throw new RuntimeException("로그인 필요");
        int userNo = Integer.parseInt(userNoStr);

        // DB에서 사용자 정보 조회
        UserEntity user = userRepository.findById(userNo)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        String userName = user.getName();  // DB에 저장된 사용자 이름 (예: "realBlog")

        BoardEntity blog = new BoardEntity();
        blog.setRegUserNo(userNo);
        blog.setType(2);
        blog.setName(userName + "의 블로그");
        // 도메인을 사용자 이름으로 설정 (예: "realBlog")
        blog.setDomain(userName);
        blog.setDescription(userName + "'s Personal Blog");
        blog.setUseYN('Y');
        blog.setRegDate(LocalDateTime.now());
        boardRepository.save(blog);

        // 기본 메뉴 생성 (없으면 생성)
        if (menuRepository.findByBoard_No(blog.getNo()).isEmpty()) {
            MenuEntity defaultMenu = new MenuEntity();
            defaultMenu.setBoard(blog);
            defaultMenu.setName("기본 메뉴");
            defaultMenu.setOrderNo(1);
            defaultMenu.setDepth(1);
            defaultMenu.setRef(0);
            defaultMenu.setUseYN('Y');
            menuRepository.save(defaultMenu);
        }
        return userName;
    }





    // 도메인으로 BoardEntity를 조회하는 헬퍼 메서드
    @Override
    public BoardEntity findBoardByDomain(String domain) {
        return boardRepository.findFirstByDomain(domain)
                .orElseThrow(() -> new RuntimeException("해당 도메인의 게시판이 없습니다."));
    }

    @Override
    public Map<BoardEntity, List<PostEntity>> getHomeData() {
        // type=2(블로그), useYN='Y'인 모든 게시판 조회
        List<BoardEntity> boards = boardRepository.findByTypeAndUseYN(2, 'Y');

        Map<BoardEntity, List<PostEntity>> result = new LinkedHashMap<>();
        for (BoardEntity b : boards) {
            List<PostEntity> recent = postRepository.findTop3ByMenu_Board_NoOrderByRegDateDesc(b.getNo());
            result.put(b, recent);
        }
        return result;
    }

    @Override
    public boolean hasBlog(int userNo) {
        return boardRepository.existsByRegUserNoAndType(userNo, 2);
    }

    @Override
    public Map<String, Object> findPostsByMenu(String domain, Integer menuNo) {
        BoardEntity board = findBoardByDomain(domain);
        MenuEntity menu = menuRepository.findById(menuNo)
                .orElseThrow(() -> new RuntimeException("해당 메뉴가 없습니다."));
        List<PostEntity> posts = postRepository.findByBoardNoOrderByRegDateDesc(board.getNo());

        Map<String, Object> result = new HashMap<>();
        result.put("board", board);
        result.put("menu", menu);
        result.put("posts", posts);
        return result;
    }

}
