package com.obra.restAPI.agenda.obras.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String ativo;
    @Column(nullable = false)
    private int quantidade;
    @Column(nullable = false)
    private String foto;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produto)) return false;
        Produto produto = (Produto) o;
        return quantidade == produto.quantidade && Objects.equals(id, produto.id) && Objects.equals(nome, produto.nome) && Objects.equals(ativo, produto.ativo) && Objects.equals(foto, produto.foto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, ativo, quantidade, foto);
    }

    @Override
    public String toString() {
        return "Obra{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", ativo='" + ativo + '\'' +
                ", quantidade=" + quantidade +
                ", foto='" + foto + '\'' +
                '}';
    }
}
