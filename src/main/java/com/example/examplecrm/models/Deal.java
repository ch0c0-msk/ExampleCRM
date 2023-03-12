package com.example.examplecrm.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "deals")
public class Deal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="client_id", referencedColumnName="id")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id", referencedColumnName="id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="created_by", referencedColumnName="id")
    private User createUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="updated_by", referencedColumnName="id")
    private User updateUser;

    @Column(name = "status")
    private String status;

    @Column(name = "create_dttm")
    private LocalDateTime createDate;

    @Column(name = "update_dttm")
    private LocalDateTime updateDate;

    @PrePersist
    private void onCreate() {

        createDate = LocalDateTime.now();
        status = "NEW";
    }

    @PreUpdate
    private void onUpdate() {

        updateDate = LocalDateTime.now();
    }
}
