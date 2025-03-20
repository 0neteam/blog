package com.java.blog.blog.service;

import com.java.blog.blog.dto.MenuDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;


public interface MenuService {
    String getMenu(Model model);

    String add(MenuDTO menuDTO);
}
