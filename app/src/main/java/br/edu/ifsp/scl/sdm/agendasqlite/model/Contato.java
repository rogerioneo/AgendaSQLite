package br.edu.ifsp.scl.sdm.agendasqlite.model;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class Contato implements Serializable {

    private int id;
    private String nome;
    private String fone;
    private String fone2;
    private String email;
    private String diaMesAniv;
    private int favorito = 0;

    public Contato() {
    }

    public Contato(String nome, String fone, String fone2, String email, String diaMesAniv) {
        this.nome = nome;
        this.fone = fone;
        this.fone2 = fone2;
        this.email = email;
        this.diaMesAniv = diaMesAniv;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return (((Contato)obj).getId() == this.getId());
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getFone2() {
        return fone2;
    }

    public void setFone2(String fone2) { this.fone2 = fone2; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getFavorito() { return favorito; }

    public void setFavorito(int favorito) { this.favorito = favorito; }

    public String getDiaMesAniv() { return diaMesAniv; }

    public void setDiaMesAniv(String diaMesAniv) { this.diaMesAniv = diaMesAniv; }
}
