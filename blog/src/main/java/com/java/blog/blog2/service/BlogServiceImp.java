package com.java.blog.blog2.service;

import com.java.blog.blog2.dto.PostDTO;
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

    @Override
    public Map<String, Object> findList(HttpServletRequest req) {
        String domain = (String) req.getAttribute("domain");
        if (domain == null || domain.isEmpty()) {
            domain = "default";
        }
        BoardEntity board = boardRepository.findFirstByDomain(domain)
                .orElseThrow(() -> new RuntimeException("해당 도메인의 게시판이 없습니다."));
        List<PostEntity> posts = postRepository.findByBoardNoOrderByRegDateDesc(board.getNo());

        // 미리보기 텍스트를 저장할 Map (key: 글번호)
        Map<Integer, String> previewMap = new HashMap<>();

        // 정규식을 사용하여 "text":"..." 값을 추출 (대소문자 구분 없음)
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\"text\"\\s*:\\s*\"(.*?)\"", java.util.regex.Pattern.CASE_INSENSITIVE);

        for (PostEntity post : posts) {
            String content = post.getContent();  // EditorJS JSON 문자열
            String preview = "";
            if (content != null && !content.isEmpty()) {
                java.util.regex.Matcher matcher = pattern.matcher(content);
                if (matcher.find()) {
                    preview = matcher.group(1);
                    if (preview.length() > 60) {
                        preview = preview.substring(0, 60) + "...";
                    }
                }
            }
            previewMap.put(post.getNo(), preview);
        }

        System.out.println("조회된 board.no: " + board.getNo());
        System.out.println("조회된 posts 수: " + posts.size());
        Map<String, Object> result = new HashMap<>();
        result.put("posts", posts);
        result.put("previewMap", previewMap);
        return result;
    }


    @Override
    public List<MenuEntity> findBoardNo(int boardNo) {
        return menuRepository.findByBoard_No(boardNo);
    }

    @Override
    @Transactional
    public int savePost(PostDTO postDTO, String domain, HttpServletRequest req) {
        String userNoStr = utils.getUserNo(req);
        if (userNoStr == null || userNoStr.isEmpty()) {
            throw new RuntimeException("로그인이 필요합니다.");
        }
        int userNo = Integer.parseInt(userNoStr);

        MenuEntity menu = menuRepository.findById(postDTO.getMenuNo())
                .orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다."));
        PostEntity post = new PostEntity();
        post.setMenu(menu);
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setRegUserNo(userNo);
        post.setRegDate(LocalDateTime.now());
        post.setViewCount(0);
        post.setUseYN('Y');

        PostEntity saved = postRepository.save(post);
        return saved.getNo();
    }










    @Override
    @Transactional
    public String createBlog(HttpServletRequest req) {
        // JWT에서 userNo 추출
        String userNoStr = utils.getUserNo(req);
        if (userNoStr == null) throw new RuntimeException("로그인이 필요합니다.");
        int userNo = Integer.parseInt(userNoStr);

        // DB에서 사용자 정보 조회
        UserEntity user = userRepository.findById(userNo)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        String userName = user.getName();  // 기본 도메인 값

        // 중복 체크: 같은 type(블로그, 즉 2)과 같은 domain이 이미 존재하는지 확인
        boolean exists = boardRepository.existsByDomainAndType(userName, 2);
        if (exists) {
            throw new RuntimeException("이미 사용 중인 도메인입니다. 도메인을 변경해주세요.");
        }

        BoardEntity blog = new BoardEntity();
        blog.setRegUserNo(userNo);
        blog.setType(2);
        blog.setName(userName + "의 블로그");
        blog.setDomain(userName);  // 기본적으로 사용자 이름을 도메인으로 설정
        blog.setDescription(userName + "'s Personal Blog");
        blog.setUseYN('Y');
        blog.setRegDate(LocalDateTime.now());
        boardRepository.save(blog);

        // 기본 메뉴 생성 (없으면 생성)
        if (menuRepository.findByBoard_No(blog.getNo()).isEmpty()) {
            MenuEntity defaultMenu = new MenuEntity();
            defaultMenu.setBoard(blog);
            defaultMenu.setName("낙서장");
            defaultMenu.setOrderNo(1);
            defaultMenu.setDepth(1);
            defaultMenu.setRef(0);
            defaultMenu.setUseYN('Y');
            menuRepository.save(defaultMenu);
        }
        return userName;
    }


    @Override
    public BoardEntity findBoardByDomain(String domain) {
        return boardRepository.findFirstByDomain(domain)
                .orElseThrow(() -> new RuntimeException("해당 도메인의 게시판이 없습니다."));
    }

    @Override
    public Map<BoardEntity, List<PostEntity>> getHomeData() {
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
