package br.com.socialenari.socialEnari.model;

import java.util.UUID;

public class Comentario {
    private UUID id;
    private String conteudo;
    private Usuario usuario;
    private int publicacaoId;

    public Comentario(UUID id, String conteudo, Usuario usuario, int publicacaoId) {
        this.id = id;
        this.conteudo = conteudo;
        this.usuario = usuario;
        this.publicacaoId = publicacaoId;
    }

    // Getters e Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getPublicacaoId() {
        return publicacaoId;
    }

    public void setPublicacaoId(int publicacaoId) {
        this.publicacaoId = publicacaoId;
    }
}
