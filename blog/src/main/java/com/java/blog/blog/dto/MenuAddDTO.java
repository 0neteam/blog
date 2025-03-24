package com.java.blog.blog.dto;

import com.java.blog.entity.MenuEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MenuAddDTO {

    private String name;

    private Integer ref;

    private Integer depth;

    private Integer orderNo;


}
