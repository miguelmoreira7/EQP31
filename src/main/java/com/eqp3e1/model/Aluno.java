package com.eqp3e1.model;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "aluno")
public class Aluno {

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

    @ManyToMany
    @JoinTable(
            name = "aluno_habilidade",
            joinColumns = @JoinColumn(name = "aluno_id"),
            inverseJoinColumns = @JoinColumn(name = "habilidade_id")
    )
    private List<Habilidade> habilidades;

    @OneToMany(mappedBy = "aluno")
    private List<Estagio> estagios;

    // Métodos
    public void candidatar(OfertaEstagio oferta) {
        // Implementação para candidatura
    }

    public List<Estagio> consultarCandidaturas() {
        // Implementação para consultar candidaturas
        return estagios;
    }

    // Getters and Setters
}