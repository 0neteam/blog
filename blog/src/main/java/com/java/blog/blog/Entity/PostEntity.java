package com.java.blog.blog.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "post")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;

    private int menuNo;
    private String title;
    private String content;
    private int regUserNo;
    private LocalDate regDate;
    private int viewCount;
    private Integer modUserNo;
    private LocalDate modDate;
    private char useYN;
}
