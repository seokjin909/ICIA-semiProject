package com.iciaproject.icia_library.entity;

import lombok.Data;
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
    private String m_id;

    @Column(nullable = false, length = 30)
    private String m_pwd;

    @Column(nullable = false, length = 20)
    private String m_name;

    @Column(nullable = false, length = 20)
    private String m_phone;

    @Column(nullable = false, length = 20)
    private String m_birth;
}
