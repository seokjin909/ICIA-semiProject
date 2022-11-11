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
    private int b_id;

    @Column(nullable = false, length = 20)
    private String b_name;

    @Column(nullable = false, length = 10)
    private String b_author;

    @Column
    @CreationTimestamp
    @JsonFormat(pattern = "YY-MM-dd")
    private Timestamp b_sdate;

    @Column
    private Timestamp b_edate;

    @ColumnDefault("0")
    @Column(nullable = false)
    private Boolean b_lent;

}
