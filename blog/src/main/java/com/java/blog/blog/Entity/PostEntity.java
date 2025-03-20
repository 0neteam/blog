package com.java.blog.blog.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "post")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer no;

    @ManyToOne
    @JoinColumn(name="menuNo", referencedColumnName = "no", nullable = false)
    MenuEntity menu;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private Integer regUserNo;

    @Column(nullable = false)
    private LocalDateTime regDate;

    @Column
    private Integer modUserNo;

    @Column
    private LocalDateTime modDate;

    @Column(nullable = false)
    private Integer viewCount;

    @Column(nullable = false)
    private String useYN;

}
