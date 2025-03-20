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
//@Table(name = "board")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class BoardEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer no;
//
//    @Column(nullable = false, length = 30)
//    private String name;
//
//    @Column(nullable = false)
//    private Date regDate;
//
//    @Column(nullable = false, length = 30)
//    private String domain;
//
//    @Column(nullable = false, length = 255)
//    private String description;
//
//    @Column(nullable = false, columnDefinition = "CHAR(1)")
//    private String useYN;
//
//}