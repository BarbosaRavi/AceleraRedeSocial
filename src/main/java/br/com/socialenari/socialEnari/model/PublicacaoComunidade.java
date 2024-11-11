package br.com.socialenari.socialEnari.model;

import java.time.LocalDateTime;

public class PublicacaoComunidade {
    private static int contadorId = 0; // Contador para gerar IDs únicos
    private int id;
    private Usuario usuario; // Referência ao objeto Usuario
    private String conteudo;
    private LocalDateTime dataHora;
    private Comunidade comunidade; // Referência à Comunidade

    public PublicacaoComunidade(Usuario usuario, String conteudo, LocalDateTime dataHora, Comunidade comunidade) {
        this.id = ++contadorId; // Atribui um ID único a cada nova publicação
        this.usuario = usuario;
        this.conteudo = conteudo;
        this.dataHora = dataHora;
        this.comunidade = comunidade;
    }

    // Getters e Setters
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

    public Comunidade getComunidade() {
        return comunidade;
    }
}
