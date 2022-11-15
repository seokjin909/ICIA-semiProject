package com.iciaproject.icia_library.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "manager")
@Data
public class Manager {

    @Id
    @Column(nullable = false, length = 20)
    private String managerid;

    @Column(nullable = false, length = 30)
    private String managerpwd;

    @Column(nullable = false, length = 20)
    private String managername;

}
