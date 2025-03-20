package com.java.blog.blog.service;

import com.java.blog.blog.dto.MenuAddDTO;
import org.springframework.ui.Model;


public interface MenuService {
    String getMenu(Model model);

    String add(MenuAddDTO menuAddDTO);
}
