package br.com.socialenari.socialEnari.model;

import java.time.LocalDateTime;

public class Publicacao {
    private String conteudo;
    private String usuario;
    private LocalDateTime dataHora; // Novo campo

    public Publicacao() {
        this.dataHora = LocalDateTime.now(); // Define a data/hora ao criar a publicação
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }
}
