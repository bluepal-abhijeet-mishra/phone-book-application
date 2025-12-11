package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "contacts")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phoneNumber;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
