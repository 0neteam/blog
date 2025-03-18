//package com.java.blog.blog2.Entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.Date;
//
//@Entity
//@Table(name = "post")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class PostEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer no;
//
//    @ManyToOne
//    @JoinColumn(name="menuNo", referencedColumnName = "no", nullable = false)
//    MenuEntity menu;
//
//    @Column(nullable = false, length = 100)
//    private String title;
//
//    @Column(nullable = false, columnDefinition = "TEXT")
//    private String content;
//
//    @Column(nullable = false)
//    private Date regUserNo;
//
//    @Column(nullable = false)
//    private Date regDate;
//
//    @Column
//    private Date modUserNo;
//    private Date modDate;
//
//    @Column(nullable = false)
//    private Integer viewCount;
//
//    @Column(nullable = false)
//    private char useYN;
//
//}
