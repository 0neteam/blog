package com.java.blog.blog.service;


import com.java.blog.blog.dto.MenuAddDTO;
import com.java.blog.blog.entity.BoardEntity;
import com.java.blog.blog.entity.MenuEntity;
import com.java.blog.blog.repository.BoardRepository;
import com.java.blog.blog.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MenuServiceImp implements MenuService {

    private final MenuRepository menuRepository;
    private final BoardRepository boardRepository;

    @Override
    public String getMenu(Model model) {
        List<MenuEntity> menuEntities = menuRepository.findByBoardNoAndUseYN(1, 'Y');
        //System.out.printf("list of menuEntities: "+menuEntities+"\n");
        model.addAttribute("menuList",menuEntities);
        return "menumanage";
    }

    @Override
    public String add(MenuAddDTO menuAddDTO) {
        //여기 코드들은 하드코딩된것들이 많음. 추후 수정필요.

        //BoardEntity boardEntity = new BoardEntity();
        BoardEntity boardEntity = boardRepository.findByNo(2);

        System.out.printf("\n boardEntity 값은 "+boardEntity+"\n");
        System.out.printf("\n menuAddDTO 값은 "+menuAddDTO+"\n");

        menuRepository.save(
                MenuEntity.builder()
                        .board(boardEntity) //타입이 BoardEntity 이어야함.
                        .name(menuAddDTO.getName())
                        .ref(menuAddDTO.getRef())
                        .depth(menuAddDTO.getDepth())
                        .orderNo(menuAddDTO.getOrderNo())
                        .build()
        );
        //MenuEntity menuEntity = menuRepository.save(menuAddDTO.toEntity());

        // 또는 적절한 뷰 이름 반환하도록 수정하지 않으면 뻗음.
        return "";
    }
}
