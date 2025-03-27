package com.java.blog.blog2.controller;

import com.java.blog.blog.dto.MenuDTO;
import com.java.blog.blog.repository.BoardRepository;
import com.java.blog.blog.repository.MenuRepository;
import com.java.blog.blog2.repository.PostRepository;
import com.java.blog.blog2.service.BlogService;
import com.java.blog.entity.BoardEntity;
import com.java.blog.entity.MenuEntity;
import com.java.blog.entity.PostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/{domain}")
public class BlogMenuController {

    private final BlogService blogService;

    @GetMapping("/{menuNo:[0-9]+}")
    public String showMenu(
            @PathVariable String domain,
            @PathVariable Integer menuNo,
            Model model) {
        getMenu(domain,model);
        Map<String, Object> data = blogService.findPostsByMenu(domain, menuNo);
        model.addAllAttributes(data);
        return "blogList";
    }

    @Qualifier(value = "boardRepository1") //메뉴 불러오는 목적으로 가져옴. - 강승우
    private final BoardRepository boardRepository;
    @Qualifier(value = "menuRepository1")
    private final MenuRepository menuRepository;
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
}