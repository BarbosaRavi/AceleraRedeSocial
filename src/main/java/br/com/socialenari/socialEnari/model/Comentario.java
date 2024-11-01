package br.com.socialenari.socialEnari.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Comentario {
    private UUID ComentarioId; // ID do comentário
    private String conteudo;
    private Usuario usuario;
    private int publicacaoId;
    private LocalDateTime dataComentario; // Adicionado campo para data e hora

    public Comentario(UUID id, String conteudo, Usuario usuario, int publicacaoId, UUID ComentarioId) {
        this.ComentarioId = ComentarioId;
        this.conteudo = conteudo;
        this.usuario = usuario;
        this.publicacaoId = publicacaoId;
        this.dataComentario = LocalDateTime.now(); // Inicializa a data e hora com o momento da criação
    }

    // Getters e Setters
    public UUID getId() {
        return ComentarioId;
    }

    public void setId(UUID id) {
        this.ComentarioId = id;
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

    public LocalDateTime getDataHora() {
        return dataComentario;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataComentario = dataHora;
    }
}
