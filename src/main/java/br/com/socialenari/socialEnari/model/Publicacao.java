package br.com.socialenari.socialEnari.model;

import java.time.LocalDateTime;

public class Publicacao {

    private static int contadorId = 0; // Contador estático para gerar IDs únicos
    private int id; // Identificador único da publicação
    private String usuario; // Nome do usuário que fez a publicação
    private String conteudo; // Conteúdo da publicação
    private LocalDateTime dataHora; // Data e hora da publicação

    /**
     * Construtor da classe Publicacao.
     * A cada nova instância, um ID único é gerado automaticamente.
     */
    public Publicacao(String usuario, String conteudo, LocalDateTime dataHora) {
        this.id = contadorId++; // Incrementa o ID para garantir que seja único
        this.usuario = usuario;
        this.conteudo = conteudo;
        this.dataHora = dataHora;
    }

    // Método getter para o campo 'id'
    public int getId() {
        return id;
    }

    // Método getter para o campo 'usuario'
    public String getUsuario() {
        return usuario;
    }

    // Método getter para o campo 'conteudo'
    public String getConteudo() {
        return conteudo;
    }

    // Método getter para o campo 'dataHora'
    public LocalDateTime getDataHora() {
        return dataHora;
    }
}
