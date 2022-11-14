package com.iciaproject.icia_library.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "board")
@Data
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bnum;

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
