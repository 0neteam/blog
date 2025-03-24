package com.java.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "board")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer no;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 30)
    private String domain;

    @Column(nullable = false)
    private LocalDateTime regDate;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, columnDefinition = "CHAR")
    private String useYN;

}