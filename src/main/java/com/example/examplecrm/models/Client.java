package com.example.examplecrm.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "create_dttm")
    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="created_by", referencedColumnName="id")
    private User user;

    @Column(name = "reject_flag")
    private Boolean rejectFlag;

    @PrePersist
    private void onCreate() {

        createDate = LocalDateTime.now();
        discount = 1.0;
        rejectFlag = false;
    }

}
