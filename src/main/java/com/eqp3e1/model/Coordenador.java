package com.eqp3e1.model;

import jakarta.persistence.*;

@Entity
@Table(name = "coordenador")
public class Coordenador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String senha;

    // Métodos
    public void alterarCadastroEmpresa(Empresa empresa) {
        // Implementação para alterar cadastro de empresa
    }

    public void acompanharCandidatos() {
        // Implementação para acompanhar candidatos
    }

    public void acompanharOfertas() {
        // Implementação para acompanhar ofertas
    }

    public void acompanharEstagios() {
        // Implementação para acompanhar estágios
    }

    // Getters and Setters
}