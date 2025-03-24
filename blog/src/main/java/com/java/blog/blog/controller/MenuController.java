package com.java.blog.blog.controller;

import com.java.blog.blog.dto.MenuAddDTO;
import com.java.blog.blog.dto.MenuDeleteDTO;
import com.java.blog.blog.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/menu")
@RequiredArgsConstructor
@Controller
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/manage")
    public String menuManage(Model model){
        return menuService.getMenu(model);
    }

    //새로운 메뉴 추가
    @PostMapping("/add")
    public String add(@RequestBody MenuAddDTO menuAddDTO){
        return menuService.add(menuAddDTO);
    }

    //메뉴 제거
    @PostMapping("/delete")
    public String delete(@RequestBody List<MenuDeleteDTO> menuDeleteDTOS){
        return menuService.delete(menuDeleteDTOS);
    }
}
