package com.example.examplecrm.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "label")
    private String label;

    @Column(name = "importance")
    private String importance;

    @Column(name = "status")
    private String status;

    @Column(name = "text")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="created_by", referencedColumnName="id")
    private User createUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="updated_by", referencedColumnName="id")
    private User updateUser;

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
