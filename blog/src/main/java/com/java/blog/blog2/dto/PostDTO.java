package com.java.blog.blog2.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDTO {

    private Integer no;
    private Integer menuNo;
    private String title;
    private String content;
    private Integer regUserNo;
    @JsonFormat(pattern = "yyyy.MM.dd. HH:mm")
    private LocalDateTime regDate;
    private Integer modUserNo;
    @JsonFormat(pattern = "yyyy.MM.dd. HH:mm")
    private LocalDateTime modDate;
    private Integer viewCount;
    private char useYN;
    private String regName;
    private String menuName;

}