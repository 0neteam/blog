package com.java.blog.blog.controller;

import com.java.blog.blog.dto.MenuAddDTO;
import com.java.blog.blog.entity.BoardEntity;
import com.java.blog.blog.entity.MenuEntity;
import com.java.blog.blog.repository.BoardRepository;
import com.java.blog.blog.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/menu")
@RequiredArgsConstructor
@Controller
public class MenuController {

    private final MenuService menuService;

    //add 메서드 에서 1번 보드 즉 블로그에 하드코딩하기위해 추가
    private final BoardRepository boardRepository;

    @GetMapping("/manage")
    public String menuManage(Model model){
        return menuService.getMenu(model);
    }

    //새로운 메뉴 추가
    @PostMapping("/add")
    public String add(@ModelAttribute MenuAddDTO menuAddDTO){




        return menuService.add(menuAddDTO);
        //return menuService.add(menuAddDTO);
    }
}
