package com.java.blog.blog.service;


import com.java.blog.blog.dto.MenuDTO;
import com.java.blog.blog.entity.BoardEntity;
import com.java.blog.blog.entity.MenuEntity;
import com.java.blog.blog.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MenuServiceImp implements MenuService {

    private final MenuRepository menuRepository;

    @Override
    public String getMenu(Model model) {
        List<MenuEntity> menuEntities = menuRepository.findByBoardNoAndUseYN(1, 'Y');
        //System.out.printf("list of menuEntities: "+menuEntities+"\n");
        model.addAttribute("menuList",menuEntities);
        return "menumanage";
    }

    @Override
    public String add(MenuDTO menuDTO) {
        //여기 코드들은 하드코딩된것들이 많음. 추후 수정필요.
        System.out.printf("menuDTO 의 값은: "+menuDTO+" 끝 ");
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setNo(1);

        MenuEntity menuEntity = MenuEntity.builder()
                .board(boardEntity)
                .orderNo(1)
                .depth(menuDTO.getDepth())
                .name(menuDTO.getName())
                .ref(menuDTO.getRef())
                .useYN('Y')
                .build();
        System.out.printf("menuEntity 의 값은: "+menuEntity+" 끝 ");
        menuRepository.save(menuEntity);

//        MenuEntity menuEntity = menuRepository.save(menuDTO.toEntity());

        return "";
    }
}
