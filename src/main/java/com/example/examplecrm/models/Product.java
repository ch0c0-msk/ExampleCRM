package com.example.examplecrm.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "create_dttm")
    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="created_by", referencedColumnName="id")
    private User createUser;

    @Column(name = "update_dttm")
    private LocalDateTime updateDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="updated_by", referencedColumnName="id")
    private User updateUser;

    @PrePersist
    private void onCreate() {
        createDate = LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdate() {

        updateDate = LocalDateTime.now();
    }
}
