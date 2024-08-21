package com.eqp3e1.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "estagio")
public class Estagio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "oferta_estagio_id", nullable = false)
    private OfertaEstagio ofertaEstagio;

    @ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    @Column(nullable = false)
    private LocalDate dataInicio;

    @Column(nullable = false)
    private LocalDate dataTermino;

    @Column
    private BigDecimal valorBolsa;

    // Métodos
    public void baixarTermoEstagio() {
        // Implementação para baixar termo de estágio
    }

    // Getters and Setters
}