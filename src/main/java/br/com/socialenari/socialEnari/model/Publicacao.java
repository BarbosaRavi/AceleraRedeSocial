package br.com.socialenari.socialEnari.model;

import java.time.LocalDateTime;

public class Publicacao {
    private static int contadorId = 0;
    private int id;
    private Usuario usuario;
    private String conteudo;
    private LocalDateTime dataHora;

    public Publicacao(Usuario usuario2, String conteudo, LocalDateTime dataHora) {
        this.id = ++contadorId;
        this.usuario = usuario2;
        this.conteudo = conteudo;
        this.dataHora = dataHora;
    }

    public int getId() {
        return id;
    }

    public Usuario getUsuario() { // Mude o tipo de retorno para Usuario
        return usuario;
    }

    public String getConteudo() {
        return conteudo;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }
}
