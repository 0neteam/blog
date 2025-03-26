package com.java.blog.blog.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
@Setter
@Getter
@ToString
@Builder
public class BlogListDTO {

    private Integer no;
    private String title;
    private String thumbnailUrl;
    private LocalDateTime regDate;

}
