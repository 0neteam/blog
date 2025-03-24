package com.java.blog.blog.service;

import com.java.blog.blog.dto.MenuAddDTO;
import com.java.blog.blog.dto.MenuDeleteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;


public interface MenuService {
    String getMenu(Model model);

    String add(MenuAddDTO menuAddDTO);

    String delete(List<MenuDeleteDTO> menuDeleteDTOS);
}
