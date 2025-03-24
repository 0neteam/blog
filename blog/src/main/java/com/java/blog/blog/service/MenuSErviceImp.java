package com.java.blog.blog.service;


import com.java.blog.entity.MenuEntity;
import com.java.blog.blog.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MenuSErviceImp implements MenuService {

    @Qualifier(value = "menuRepository1")
    private final MenuRepository menuRepository;

    @Override
    public String getMenu(Model model) {
        List<MenuEntity> menuEntities = menuRepository.findByBoardNoAndUseYN(1, "Y");
        //System.out.printf("list of menuEntities: "+menuEntities+"\n");
        model.addAttribute("menuList",menuEntities);
        return "menumanage";
    }
}
