package com.iciaproject.icia_library.entity;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "member")
@Data
public class Member {
    @Id
    @Column(nullable = false, length = 20)
    private String mid;

    @Column(nullable = false, length = 30)
    private String mpwd;

    @Column(nullable = false, length = 20)
    private String mname;

    @Column(nullable = false, length = 20)
    private String mphone;

    @Column(nullable = false, length = 20)
    private String mbirth;

    @ColumnDefault("0")
    @Column(nullable = false)
    private int count;
}
