package com.obra.restAPI.agenda.obras.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Obra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    private String ativo;
    @Column(nullable = false)
    private int quantidade;
    private String foto;
    private String sessao;

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

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getSessao() {
        return sessao;
    }

    public void setSessao(String sessao) {
        this.sessao = sessao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Obra obra = (Obra) o;
        return quantidade == obra.quantidade && Objects.equals(id, obra.id) && Objects.equals(nome, obra.nome) && Objects.equals(ativo, obra.ativo) && Objects.equals(foto, obra.foto) && Objects.equals(sessao, obra.sessao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, ativo, quantidade, foto, sessao);
    }

    @Override
    public String toString() {
        return "Obra{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", ativo='" + ativo + '\'' +
                ", quantidade=" + quantidade +
                ", foto='" + foto + '\'' +
                ", sessao='" + sessao + '\'' +
                '}';
    }
}
