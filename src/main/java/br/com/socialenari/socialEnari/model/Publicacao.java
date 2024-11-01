package br.com.socialenari.socialEnari.model;

import java.time.LocalDateTime;

public class Publicacao {
    private static int contadorId = 0; // contadorId deve ser estático para manter a contagem entre instâncias
    private int id;
    private Usuario usuario;
    private String conteudo;
    private LocalDateTime dataHora;

    public Publicacao(Usuario usuario, String conteudo, LocalDateTime dataHora) {
        this.id = ++contadorId; // Incrementar o contador ao criar uma nova publicação
        this.usuario = usuario;
        this.conteudo = conteudo;
        this.dataHora = dataHora;
    }

    public int getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getConteudo() {
        return conteudo;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }
}
