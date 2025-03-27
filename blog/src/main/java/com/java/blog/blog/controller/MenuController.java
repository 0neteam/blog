package com.java.blog.blog.controller;

import com.java.blog.blog.dto.MenuAddDTO;
import com.java.blog.blog.dto.MenuDTO;
import com.java.blog.blog.dto.MenuDeleteDTO;
import com.java.blog.blog.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/{domain}/menu")
@RequiredArgsConstructor
@Controller
public class MenuController {

    private final MenuService menuService;

    
    //메뉴 관리 화면에서 메뉴들을 화면에 뿌려주는 목적의 메서드
    @GetMapping
    public String menuManage(@PathVariable String domain, Model model){
        return menuService.getMenu(domain, model);
    }

    //새로운 메뉴 추가
    @PutMapping
    @ResponseBody
    public String add(@PathVariable String domain, @RequestBody MenuDTO menuDTO){
        return menuService.add(domain, menuDTO);
    }

    //메뉴수정
    @PatchMapping
    @ResponseBody
    public String edit(@PathVariable String domain, @RequestBody MenuDTO menuDTO){
        return menuService.edit(domain, menuDTO);
    }

    //메뉴 제거
    @DeleteMapping("/{no:[0-9]+}")
    @ResponseBody
    public String delete(@PathVariable String domain, @PathVariable("no") Integer no){
        return menuService.delete(domain, no);
    }
}
