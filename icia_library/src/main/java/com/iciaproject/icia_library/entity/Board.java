package com.iciaproject.icia_library.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bnum;

    @Column(nullable = false, length = 45)
    private String btitle;

    @Column(nullable = false, length = 20)
    private String bwriter;

    @Column(nullable = false, length = 20)
    private String bpwd;

    @Column(nullable = false)
    private String bcontent;

    @Column
    @CreationTimestamp
    private Timestamp rdate;

}
