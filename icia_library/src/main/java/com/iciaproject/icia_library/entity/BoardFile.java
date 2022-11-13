package com.iciaproject.icia_library.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "boardfiletbl")
@Data
public class BoardFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bfnum;

    @ManyToOne
    @JoinColumn(name = "bfbid")
    private Board bfbid;

    @Column(nullable = false, length = 30)
    private String bfsysname;

    @Column(nullable = false, length = 30)
    private String bforiname;
}
