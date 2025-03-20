package com.java.blog.blog.service;


import com.java.blog.blog.entity.MenuEntity;
import com.java.blog.blog.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MenuSErviceImp implements MenuService {

    private final MenuRepository menuRepository;

    @Override
    public String getMenu(Model model) {
        List<MenuEntity> menuEntities = menuRepository.findByBoardNoAndUseYN(1, "Y");
        //System.out.printf("list of menuEntities: "+menuEntities+"\n");
        model.addAttribute("menuList",menuEntities);
        return "menumanage";
    }
}
