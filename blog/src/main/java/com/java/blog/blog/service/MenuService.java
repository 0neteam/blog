package com.java.blog.blog.service;

import com.java.blog.blog.dto.MenuAddDTO;
import com.java.blog.blog.dto.MenuDTO;
import com.java.blog.blog.dto.MenuDeleteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;


public interface MenuService {
    String getMenu(String domain, Model model);

    String add(String domain, MenuDTO menuDTO);

    String delete(String domain, Integer no);

    String edit(String domain, MenuDTO menuDTO);
}
