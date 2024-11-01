package br.com.socialenari.socialEnari.model;

import java.time.LocalDateTime;

public class Publicacao {
    private static int contadorId = 0; // Contador para gerar IDs únicos
    private int id;
    private Usuario usuario; // Referência ao objeto Usuario
    private String conteudo;
    private LocalDateTime dataHora;

    public Publicacao(Usuario usuario, String conteudo, LocalDateTime dataHora) {
        this.id = ++contadorId; // Atribui um ID único a cada nova publicação
        this.usuario = usuario; // Atribui o objeto Usuario
        this.conteudo = conteudo;
        this.dataHora = dataHora;
    }

    public int getId() {
        return id; // Retorna o ID da publicação
    }

    public Usuario getUsuario() {
        return usuario; // Retorna o objeto Usuario
    }

    public String getConteudo() {
        return conteudo;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }
}
