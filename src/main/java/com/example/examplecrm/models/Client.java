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

    @Column(name = "discount", columnDefinition = "real default 1.0")
    private Double discount;

    @Column(name = "create_dttm")
    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="created_by", referencedColumnName="id")
    private User user;

    @Column(name = "reject_flag", columnDefinition = "boolean default false")
    private Boolean rejectFlag = false;

    @PrePersist
    private void onCreate() {
        createDate = LocalDateTime.now();
    }

}
