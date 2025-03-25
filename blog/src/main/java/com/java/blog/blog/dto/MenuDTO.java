package com.java.blog.blog.dto;

import com.java.blog.entity.MenuEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
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