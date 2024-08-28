package com.eqp3e1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;



import java.util.List;

@Entity
@Table(name = "empresa")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @Column(nullable = false, unique = true, length = 14)
    @NotBlank(message = "O CNPJ é obrigatório")
    @Pattern(regexp = "\\d{14}", message = "CNPJ deve ter 14 dígitos")
    private String cnpj;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "O endereço é obrigatório")
    private String endereco;

    @Column(length = 20)
    @NotBlank(message = "O telefone é obrigatório")
    private String telefone;

    @Column(length = 100)
    @NotBlank(message = "O e-mail é obrigatório")
    private String email;

    @Column(nullable = false , length = 100)
    @NotBlank(message = "A senha é obrigatória")
    private String senha;

    public @NotBlank(message = "A senha é obrigatória") String getSenha() {
        return senha;
    }

    public void setSenha(@NotBlank(message = "A senha é obrigatória") String senha) {
        this.senha = senha;
    }

    @OneToMany(mappedBy = "empresa")
    private List<OfertaEstagio> ofertas;

    @OneToMany(mappedBy = "empresa")
    private List<Estagio> estagios;

    public List<Estagio> getEstagios() {
        return estagios;
    }

    public void setEstagios(Estagio estagio) {
        this.estagios.add(estagio);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<OfertaEstagio> getOfertas() {
        return ofertas;
    }

    public void setOfertas(OfertaEstagio oferta) {
        this.ofertas.add(oferta);
    }
}