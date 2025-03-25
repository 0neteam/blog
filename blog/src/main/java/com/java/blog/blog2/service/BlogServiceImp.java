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
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
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

    // 게시글 목록 조회: request attribute "domain"를 통해 해당 BoardEntity를 조회하고, 그 board에 속하는 글들을 가져옴
    @Override
    public Map<String, Object> findList(HttpServletRequest req) {
        String domain = (String) req.getAttribute("domain");
        if(domain == null || domain.isEmpty()){
            domain = "default";
        }
        String fullDomain = "blog/list/" + domain;
        BoardEntity board = boardRepository.findFirstByDomain(fullDomain)
                .orElseThrow(() -> new RuntimeException("해당 도메인의 게시판이 없습니다."));

        // 변경: findByBoardNo 메서드를 사용하여 게시글 조회
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
            post.setRegUserNo(1); // 기본값 (추후 로그인 정보로 대체)
        }
        MenuEntity menu = menuRepository.findById(post.getMenu().getNo())
                .orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다."));
        String fullDomain = "blog/list/" + domain;
        BoardEntity board = boardRepository.findFirstByDomain(fullDomain)
                .orElseThrow(() -> new RuntimeException("게시판을 찾을 수 없습니다."));
        post.setRegDate(LocalDateTime.now());
        post.setViewCount(0);
        post.setUseYN('Y');
        post.setMenu(menu);
        // 만약 PostEntity에 BoardEntity와의 연관관계가 있다면 여기에 설정할 수 있습니다.
        postRepository.save(post);
    }

    // 블로그 생성: 요청 파라미터(userName, userId)를 통해 BoardEntity 생성 및 기본 메뉴 생성
    @Override
    @Transactional
    public String createBlog(HttpServletRequest req) {
        // 요청 파라미터에서 사용자 이름을 가져오지만, 값이 없으면 임시로 "blog"를 사용합니다.
        String userName = req.getParameter("userName");
        if (userName == null || userName.isEmpty()) {
            userName = "blog"; // user 테이블에 저장된 이름("blog")으로 설정
        }
        // userId도 임시로 9 (user no 9)로 사용합니다.
        String userIdParam = req.getParameter("userId");
        Long regUserNo;
        try {
            regUserNo = (userIdParam != null) ? Long.parseLong(userIdParam) : 9L;
        } catch (NumberFormatException e) {
            regUserNo = 9L;
        }

        BoardEntity blog = new BoardEntity();
        blog.setName(userName + "의 블로그");
        // 도메인을 "blog/list/{userName}" 형식으로 설정 (예: "blog/list/blog")
        String fullDomain = "blog/list/" + userName;
        blog.setDomain(fullDomain);
        blog.setDescription(userName + "'s Personal Blog");
        blog.setUseYN('Y');
        blog.setRegDate(LocalDateTime.now());
        blog.setRegUserNo(regUserNo.intValue());
        blog.setType(2); // 블로그 타입: 2

        boardRepository.save(blog);

        // 블로그에 연결된 메뉴가 없으면 기본 메뉴를 생성합니다.
        if (menuRepository.findByBoard_No(blog.getNo()).isEmpty()) {
            MenuEntity defaultMenu = new MenuEntity();
            // 만약 MenuEntity가 BoardEntity와 연관관계로 매핑되어 있다면 setBoard 사용
            defaultMenu.setBoard(blog);
            defaultMenu.setName("기본 메뉴");
            defaultMenu.setOrderNo(1);
            defaultMenu.setDepth(1);
            defaultMenu.setRef(0);
            defaultMenu.setUseYN('Y');
            menuRepository.save(defaultMenu);
        }

        return blog.getDomain(); // 예: "blog/list/blog"
    }


    // 도메인으로 BoardEntity를 조회하는 헬퍼 메서드
    @Override
    public BoardEntity findBoardByDomain(String domain) {
        String fullDomain = "blog/list/" + domain;
        return boardRepository.findFirstByDomain(fullDomain)
                .orElseThrow(() -> new RuntimeException("해당 도메인의 게시판이 없습니다."));
    }
}
