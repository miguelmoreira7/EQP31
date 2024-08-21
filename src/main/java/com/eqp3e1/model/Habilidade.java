package com.eqp3e1.model;

import jakarta.persistence.*;

@Entity
@Table(name = "habilidade")
public class Habilidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    // Getters and Setters
}