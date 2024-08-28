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

    @ManyToMany(mappedBy = "candidatos", fetch = FetchType.LAZY)
    private List<OfertaEstagio> candidaturas;

    public List<OfertaEstagio> getCandidaturas() {
        return candidaturas;
    }

    public void setCandidaturas(List<OfertaEstagio> candidaturas) {
        this.candidaturas = candidaturas;
    }

    public List<Habilidade> getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(List<Habilidade> habilidades) {
        this.habilidades = habilidades;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Estagio> getEstagios() {
        return estagios;
    }

    public void setEstagios(List<Estagio> estagios) {
        this.estagios = estagios;
    }
}