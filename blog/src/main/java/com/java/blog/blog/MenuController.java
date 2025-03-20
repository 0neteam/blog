package com.java.blog.blog;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String add(){
        return menuService.add();
    }
}
