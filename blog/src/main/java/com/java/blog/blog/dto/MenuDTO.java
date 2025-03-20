package com.java.blog.blog.dto;

import com.java.blog.blog.entity.MenuEntity;
import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuDTO {

    private Integer no;
    private Integer boardNo;
    private Integer orderNo;
    private Integer depth;
    private String name;
    private Integer ref;
    private char useYN;
    private List<MenuEntity> children;


}
