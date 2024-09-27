package br.com.socialenari.socialEnari.model;

import java.time.LocalDateTime;

public class Publicacao {

    private String usuario;
    private String conteudo;
    private LocalDateTime dataHora;
    private int curtidas; // Novo campo para curtidas

    public Publicacao() {
        this.curtidas = 0; // Inicializa as curtidas com zero
    }

    // Getters e Setters
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora() {
        this.dataHora = LocalDateTime.now();
    }

    public int getCurtidas() {
        return curtidas;
    }

    public void curtir() {
        this.curtidas++;
    }
}
