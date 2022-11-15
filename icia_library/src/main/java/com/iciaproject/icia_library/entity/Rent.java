package com.iciaproject.icia_library.entity;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "rent")
@Data
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long rnum;

    @OneToOne
    @JoinColumn(name = "Member")
    private Member rmember;

    @OneToOne
    @JoinColumn(name = "Book")
    private Book rbook;

    @Column(nullable = false, length = 15)
    private String rsdate;

    @Column(nullable = false, length = 15)
    private String redate;
}
