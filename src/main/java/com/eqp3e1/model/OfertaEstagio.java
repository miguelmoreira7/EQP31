package com.eqp3e1.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "oferta_estagio")
public class OfertaEstagio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, length = 200)
    private String atividadePrincipal;

    @Column(nullable = false)
    private int cargaHorariaSemanal;

    @Column
    private BigDecimal valorBolsa;

    @Column(nullable = false)
    private boolean valeTransporte;

    @ElementCollection
    @CollectionTable(name = "oferta_prerequisitos", joinColumns = @JoinColumn(name = "oferta_id"))
    @Column(name = "prerequisito")
    private List<String> preRequisitos;

    @ManyToMany
    @JoinTable(
            name = "oferta_habilidade_necessaria",
            joinColumns = @JoinColumn(name = "oferta_id"),
            inverseJoinColumns = @JoinColumn(name = "habilidade_id")
    )
    private List<Habilidade> habilidadesNecessarias;

    @ManyToMany
    @JoinTable(
            name = "oferta_habilidade_desejavel",
            joinColumns = @JoinColumn(name = "oferta_id"),
            inverseJoinColumns = @JoinColumn(name = "habilidade_id")
    )
    private List<Habilidade> habilidadesDesejaveis;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusOferta status;

    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @OneToMany(mappedBy = "ofertaEstagio")
    private List<Estagio> estagios;

    // Métodos
    public void converterEmEstagio(Aluno aluno) {
        // Implementação para converter oferta em estágio
    }

    // Getters and Setters
}