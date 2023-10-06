package com.example.findsnils;

import jakarta.persistence.*;

@Entity
@Table(name = "snils")
public class SnilsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String snils;

    // Геттеры и сеттеры
}
