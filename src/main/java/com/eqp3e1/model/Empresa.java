package com.eqp3e1.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "empresa")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 14)
    private String cnpj;

    @Column(nullable = false, length = 200)
    private String endereco;

    @Column(length = 20)
    private String telefone;

    @Column(length = 100)
    private String email;

    @Column(length = 200)
    private String url;

    @OneToMany(mappedBy = "empresa")
    private List<OfertaEstagio> ofertas;

    // Métodos
    public void registrarOferta(OfertaEstagio oferta) {
        // Implementação para registrar oferta
    }

    public void cancelarOferta(OfertaEstagio oferta) {
        // Implementação para cancelar oferta
    }

    public void selecionarCandidato(Aluno aluno, OfertaEstagio oferta) {
        // Implementação para selecionar candidato
    }

    // Getters and Setters
}