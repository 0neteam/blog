package com.java.blog.blog2.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "menu")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer no;

    @ManyToOne
    @JoinColumn(name="boardNo", referencedColumnName = "no", nullable = false)
    BoardEntity board;

    @Column(nullable = false)
    private Integer orderNo;

    @Column(nullable = false)
    private Integer depth;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false)
    private Integer ref;

    @Column(nullable = false)
    private String useYN;

}