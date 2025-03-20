package com.java.blog.blog;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;


public interface MenuService {
    String getMenu(Model model);
}
