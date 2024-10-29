package br.com.socialenari.socialEnari.model;

import java.time.LocalDateTime;

public class Publicacao {
    private static int contadorId = 0; // Contador para gerar IDs únicos
    private int id;
    private String usuario;
    private String conteudo;
    private LocalDateTime dataHora;

    public Publicacao(String usuario, String conteudo, LocalDateTime dataHora) {
        this.id = ++contadorId; // Atribui um ID único a cada nova publicação
        this.usuario = usuario;
        this.conteudo = conteudo;
        this.dataHora = dataHora;
    }

    public int getId() {
        return id; // Retorna o ID da publicação
    }

    public String getUsuario() {
        return usuario;
    }

    public String getConteudo() {
        return conteudo;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }
}
