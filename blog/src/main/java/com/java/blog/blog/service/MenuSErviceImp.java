package com.java.blog.blog.service;


import com.java.blog.blog.dto.MenuAddDTO;
import com.java.blog.blog.dto.MenuDTO;
import com.java.blog.blog.dto.MenuDeleteDTO;
import com.java.blog.blog.repository.BoardRepository;
import com.java.blog.entity.BoardEntity;
import com.java.blog.entity.MenuEntity;
import com.java.blog.blog.repository.MenuRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class MenuSErviceImp implements MenuService {

    @Qualifier(value = "menuRepository1")
    private final MenuRepository menuRepository;
    @Qualifier(value = "boardRepository1")
    private final BoardRepository boardRepository;


    // 메뉴 리스트 가져오기
    @Override
    public String getMenu(String domain, Model model) {
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
        return "menumanage";
    }

    @Override
    public String add(String domain, MenuDTO menuDTO) {
        Optional<BoardEntity> boardSelect = boardRepository.findByTypeAndDomain(2, domain);
        if (boardSelect.isEmpty()) {
            throw new IllegalArgumentException("Board not found for domain: " + domain);
        }
        BoardEntity board = boardSelect.get();

        MenuEntity menu = MenuEntity.builder()
                .board(board)
                .orderNo(1)
                .depth(menuDTO.getDepth())
                .name(menuDTO.getName())
                .ref(menuDTO.getRef())
                .useYN('Y')
                .build();
        menuRepository.save(menu);
        return "menumanage";
    }



    @Override
    public String edit(String domain, MenuDTO menuDTO) {
        MenuEntity menu = menuRepository.findById(menuDTO.getNo())
                .orElseThrow(() -> new RuntimeException("Menu not found"));
        menu.setName(menuDTO.getName());
        menu.setRef(menuDTO.getRef());
        menuRepository.save(menu);
        return "menumanage";
    }

    @Override
    public String delete(String domain, Integer no) {
        MenuEntity menuEntity = menuRepository.findByNo(no);
        menuEntity.setUseYN('N');
        menuRepository.save(menuEntity);
return "menumanage";

    }

}
