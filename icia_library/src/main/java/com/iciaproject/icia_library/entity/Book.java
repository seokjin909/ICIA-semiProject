package com.iciaproject.icia_library.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

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

    @Column
    @CreationTimestamp
    @JsonFormat(pattern = "YY-MM-dd")
    private Timestamp bsdate;

    @Column
    private Timestamp bedate;

    @ColumnDefault("0")
    @Column(nullable = false)
    private Boolean blent;

}
