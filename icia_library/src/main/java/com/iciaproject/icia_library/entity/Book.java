package com.iciaproject.icia_library.entity;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "book")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bid;

    @Column(nullable = false, length = 20)
    private String bname;

    @Column(nullable = false, length = 10)
    private String bauthor;

    @ColumnDefault("0")
    @Column(nullable = false, length = 15)
    private String bsdate;

    @ColumnDefault("0")
    @Column(nullable = false, length = 15)
    private String bedate;

    @ColumnDefault("0")
    @Column(nullable = false)
    private Boolean blent;
}
