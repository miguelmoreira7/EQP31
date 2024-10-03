package com.eqp3e1.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    @ManyToMany
    @JoinTable(
            name = "oferta_candidaturas",
            joinColumns = @JoinColumn(name = "oferta_id"),
            inverseJoinColumns = @JoinColumn(name = "aluno_id")
    )
    private List<Aluno> candidatos = new ArrayList<>();

    public void addCandidato(Aluno aluno) {
        if(!candidatos.contains(aluno)){
            candidatos.add(aluno);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAtividadePrincipal() {
        return atividadePrincipal;
    }

    public void setAtividadePrincipal(String atividadePrincipal) {
        this.atividadePrincipal = atividadePrincipal;
    }

    public List<Aluno> getCandidaturas() {
        return candidatos;
    }

    public int getCargaHorariaSemanal() {
        return cargaHorariaSemanal;
    }

    public void setCargaHorariaSemanal(int cargaHorariaSemanal) {
        this.cargaHorariaSemanal = cargaHorariaSemanal;
    }

    public BigDecimal getValorBolsa() {
        return valorBolsa;
    }

    public void setValorBolsa(BigDecimal valorBolsa) {
        this.valorBolsa = valorBolsa;
    }

    public boolean isValeTransporte() {
        return valeTransporte;
    }

    public void setValeTransporte(boolean valeTransporte) {
        this.valeTransporte = valeTransporte;
    }

    public List<String> getPreRequisitos() {
        return preRequisitos;
    }

    public void setPreRequisitos(List<String> preRequisitos) {
        this.preRequisitos = preRequisitos;
    }

    public List<Habilidade> getHabilidadesNecessarias() {
        return habilidadesNecessarias;
    }

    public void setHabilidadesNecessarias(List<Habilidade> habilidadesNecessarias) {
        this.habilidadesNecessarias = habilidadesNecessarias;
    }

    public List<Habilidade> getHabilidadesDesejaveis() {
        return habilidadesDesejaveis;
    }

    public void setHabilidadesDesejaveis(List<Habilidade> habilidadesDesejaveis) {
        this.habilidadesDesejaveis = habilidadesDesejaveis;
    }

    public StatusOferta getStatus() {
        return status;
    }

    public void setStatus(StatusOferta status) {
        this.status = status;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

}
