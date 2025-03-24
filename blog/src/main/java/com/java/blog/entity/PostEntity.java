package com.java.blog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

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

    @Column(nullable = false)
    private Integer viewCount;

    @Column
    private Integer modUserNo;

    @Column
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy.MM.dd.")
    private LocalDateTime modDate;

    @Column(nullable = false)
    private char useYN;

}
