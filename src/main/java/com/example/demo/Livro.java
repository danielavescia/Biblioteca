package com.example.demo;

public class Livro {

    private int id;
    private String titulo;
    private int anoPublicaçao;
    private String autor;

    public Livro(int id, String titulo, int anoPublicaçao, String autor) {
        this.id = id;
        this.titulo = titulo;
        this.anoPublicaçao = anoPublicaçao;
        this.autor = autor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAnoPublicação() {
        return anoPublicaçao;
    }

    public void setAnoPublicação(int anoPublicaçao) {
        this.anoPublicaçao = anoPublicaçao;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

}
